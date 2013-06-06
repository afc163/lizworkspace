#!/usr/bin/env python
#coding=utf-8


import sys, os, popen2, signal

from time import sleep
from optparse import OptionParser
from ConfigParser import SafeConfigParser, NoOptionError
from threading import Thread, enumerate, currentThread

VERSION = "0.4.0"

APP_DEFAULTS = {
    "config": os.path.expanduser("~/.remote_exec"),
    "section": "default"
}

USER_DEFAULTS = {
    "section": "default",
    "host_template": "%%s",
    "machines": "localhost",
    "ssh_options": " ".join(["-o StrictHostKeyChecking=no",
                             "-o UserKnownHostsFile=hosts",
                             "-q"]),
    "user": os.environ['USER'],
    "keyfile": "~/.ssh/id_dsa.pub"
}


class dummy(object):
    pid = None


class Ssh(Thread):
    options = {}

    def __init__(self, targets, commands):
        self.targets = targets
        self.commands = commands
        self.p = dummy()
        self.p.pid = None
        self.kill = False
        Thread.__init__(self)

    def build_cmd(self, target):
        target = self.options["host_template"] % (target)
        cmd = "ssh -i %s %s %s@%s %s" % (self.options["keyfile"],
                                         self.options["ssh_options"],
                                         self.options["user"],
                                         target, self.commands)
        #print "{{{%s}}}" % (cmd)
        return cmd

    def run(self):
        while not self.kill:
            try:
                target = self.targets.pop(0)
            except IndexError:
                return
            cmd = self.build_cmd(target)
            try:
                self.p = popen2.Popen4(cmd)
                for line in self.p.fromchild:
                    sys.stdout.write("[%s] %s" % (target, line))
            except IOError:
                    return

    def kill(self):
        self.kill = True
        sleep(0.01)
        if not self.p.pid is None:
            os.system("kill %s > /dev/null" % (self.p.pid))
        print "Killing thread"

    def nothreads(self):
        for target in self.targets:
            cmd = self.build_cmd(target)
            os.system(cmd)


def add_machines(option, opt, value, parser):
    """generates lists of target machines"""
    if opt == "-M" or opt == "--machinefile":
        try:
            machines_file = open(value)
        except IOError:
            parser.error("-f: file error in \"%s\"." % (value))
        machines_list = []
        for line in machines_file:
            for machine in line.split(","):
                machines_list.append(machine.strip())
        machines_file.close()
    elif opt == "-m" or opt == "--machines":
        machines_list = value.split(",")
    if not parser.values.machines: parser.values.machines = []
    for machine in machines_list:
        parser.values.machines.append(machine)


def add_commands(option, opt, value, parser):
    """reads list of commands from file"""
    try:
        commands_file = open(value)
    except IOError:
        parser.error("-c: file error in \"%s\"." % (value))
    parser.largs.append("\"")
    for line in commands_file:
        line = line.replace("\\", "\\\\")
        line = line.replace("$", "\$")
        line = line.replace("`", "\`")
        line = line.replace("\"", "\\\"")
        parser.largs.append(line)
    parser.largs.append("\"")


def make_config(parser, config_filename):
    pass


def do_config(parser):
    global APP_DEFAULTS, USER_DEFAULTS

    if parser.values.configfile:
        configfile = parser.values.configfile
    else:
        configfile = APP_DEFAULTS["config"]

    write_config = False
    cfgparser = SafeConfigParser()
    cfgparser.read(configfile)
    if parser.values.section:
        # 如何命令行参数中指定了section项
        section = parser.values.section
        if not cfgparser.has_section(section):
            # 如果在配置文件中没有有这个项，报错
            parser.error("Section %s (option -s) does not exist in %s." %
                         (section, configfile))
    else:
        # 命令行中没有指定section项
        section = APP_DEFAULTS["section"]
        # 如果default在配置文件中不存在，那么就写入文件中
        if not cfgparser.has_section(section):
            # 增加一个default section
            cfgparser.add_section(section)
            for key, value in USER_DEFAULTS.items():
                # 增加section中的项名，
                cfgparser.set(section, key, value)
            try:
                cfgfile = open(configfile, "w")
                # 将cfgparser对象写入文件
                cfgparser.write(cfgfile)
                cfgfile.close()
            except IOError:
                parser.error("Error writing to config file %s." % configfile)
        # 确认在配置文件中的default段中有section项，没有的话把增加一个项section，值为default
        if not cfgparser.has_option(section, "section"):
            cfgparser.set(section, "section", APP_DEFAULTS["section"])
        section = cfgparser.get(section, "section")

    # 不管当前有没有指定section项，如果发现已有的config文件中使用section的user与当前用户不准，无条件修改
    # user项值为当前用户
    if cfgparser.get(section,"user")==os.environ['USER']:
        pass
    else:
        cfgparser.set(section,"user",os.environ['USER'])
        try:
            cfgfile = open(configfile, "w")
            # 将cfgparser对象重写入文件，因为当前用户需要修改
            cfgparser.write(cfgfile)
            cfgfile.close()
        except IOError:
            psrser.error("Error writing to config file %s." % configfile)

    if not cfgparser.has_section(section):
        parser.error("Missing section \"%s\" in %s" %
                     (section, configfile))
    # 如果命令行中指定了username
    if parser.values.username:
        cfgparser.set(section, "user", parser.values.username)
        
    # 如果命令行中指定了keyfile
    if parser.values.keyfile:
        cfgparser.set(section, "keyfile", parser.values.keyfile)
    
    # 为ssh对象增加4个内容
    for opt in ["host_template", "ssh_options", "user", "keyfile"]:
        try:
            Ssh.options[opt] = cfgparser.get(section, opt)
        except NoOptionError:
            # 如果使用指定的section失败的话，那么就使用默认的section，预留功能
            try:
                Ssh.options[opt] = cfgparser.get(APP_DEFAULTS["section"], opt)
            except NoOptionError:
                parser.error("missing setting \"%s\" in %s." %
                             (opt, configfile))
    # 如果命令行中定义了多个机器
    if parser.values.allmachines:
        try:
            parser.values.machines = []
            # 多个服务器，在配置文件中以","逗号分割
            for machine in cfgparser.get(section, "machines").split(","):
                parser.values.machines.append(machine.strip())
        except NoOptionError:
            parser.error("missing \"machines\" in section [%s] in %s and "
                         "--allmachines specified." % (section, configfile))


def sigint_handler(signum, frame):
    thread = currentThread()
    if thread is Ssh:
        thread.kill()
    elif thread.getName() == "MainThread":
        print "Received ctrl-c"
        sleep(0.1)
        sys.exit(1)


def main(argv=None):
    # Do command line argument parsing
    global VERSION
    if argv is None: argv = sys.argv
    parser = OptionParser(usage="usage: %prog [options] [commands to execute]",
                          version=VERSION)
    parser.set_defaults(threads=0, allmachines=False)
    parser.add_option("-m", "--machines",
                      action="callback",
                      type="string",
                      help="comma-delimited list of target machines LIST",
                      metavar="LIST",
                      dest="machines",
                      callback=add_machines)
    parser.add_option("-M", "--machinefile",
                      action="callback",
                      type="string",
                      help="list of target machines in FILE",
                      metavar="FILE",
                      dest="machines",
                      callback=add_machines)
    parser.add_option("-a", "--allmachines",
                      action="store_true",
                      help="target allmachines machines",
                      dest="allmachines")
    parser.add_option("-c", "--configfile",
                      action="store",
                      type="string",
                      help="read configuration from FILE",
                      metavar="FILE",
                      dest="configfile")
    parser.add_option("-s", "--section",
                      action="store",
                      type="string",
                      help="use settings in section SEC from config file",
                      metavar="SEC",
                      dest="section")
    parser.add_option("-u", "--user",
                      action="store",
                      type="string",
                      help="log in as USER on remote systems",
                      metavar="USER",
                      dest="username")
    parser.add_option("-k", "--key",
                      action="store",
                      type="string",
                      help="use the private key in FILE to log in",
                      metavar="FILE",
                      dest="keyfile")
    parser.add_option("-f", "--commandfile",
                      action="callback",
                      type="string",
                      help="execute commands in FILE on remote machines",
                      metavar="FILE",
                      callback=add_commands)
    parser.add_option("-t", "--threads",
                      action="store",
                      type="int",
                      help="run NUM simlutaneous threads",
                      metavar="NUM",
                      dest="threads")
    # 命令行解析
    (options, args) = parser.parse_args()

    # 如果没有指定机器名，返回报错
    if not (options.machines or options.allmachines):
        parser.error("You need to give target machines using -m, -f, or -a.")
    # 进行配置文件解析
    do_config(parser)

    # 给定中断信号，执行sigint_handler
    signal.signal(signal.SIGINT, sigint_handler)

    # Do the ssh stuff we're actually here for
    if options.threads > 0:
        if len(args) == 0:
            parser.error("You cannot run interactively using -t.")
        # 实现多线程方式同时登陆机器执行操作
        for item in range(options.threads):
            Ssh(options.machines, " ".join(args)).start()
    else:
        Ssh(options.machines, " ".join(args)).nothreads()


if __name__ == "__main__":
    sys.exit(main())
