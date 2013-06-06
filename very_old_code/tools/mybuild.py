# coding=utf-8

""" My Build Util
    merge js/css based config
    @author: qiaohua
    @contract: qiaohua@taobao.com
    @version: 0.1
    @depend: python2.5+
    @todo: 1) codedetect: auto detect the encode of file, see: http://chardet.feedparser.org/;
"""

import os, sys
import re
import logging
import logging.config
from optparse import OptionParser

logging.config.fileConfig("logging.conf")
logger = logging.getLogger("LG")

class MyBuilder(object):
    """ 
    """
    SEPAERATER = '====='
    
    def __init__(self, config, dest, zip=''):
        """
        @param config: the absolute path of config file
        @type config: string
        @param dest: the absolute path of destnate dir
        @type dest: string
        """
        self.config = config
        self.dest = dest
        self.srcPt = re.compile(r'src="(.*?)"')
        self.cssPt = re.compile(r'href="(.*?)"')
        self.cmtPt = re.compile(r"'''(.|\n)*?'''")
        self.zip = zip

    def run(self):
        """
        """
        #os.chdir(os.path.dirname(self.config))
        
        for fstr in self.cmtPt.sub('', ''.join(open(self.config).readlines())).split(MyBuilder.SEPAERATER):
            fn = fstr.strip().split('\n')[0].split()[0]
            self.__getItem__(fn, fstr)
        
        logger.info('\nDone! check the dir: \n%s\n' % self.dest)
        
        # change back to current path
        #os.chdir(os.path.abspath(__file__))
    
    def __getItem__(self, filename, fstr):
        """ handle one item
        @param filename: items filename, without file suffix
        @type filename: string
        """
        
        fileList = self.srcPt.findall(fstr)
        if fileList:
            suffix = 'js'
        else:
            fileList = self.cssPt.findall(fstr)
            suffix = 'css'
        
        if fileList:
            filenameM = filename+'-min.'+suffix
            filename = filename+'.'+suffix
            
            content = []
            for s in fileList:
                s = os.path.normpath(os.path.join(os.path.dirname(self.config), sys.platform == 'win32' and s.replace('/', '\\') or s))
                content.extend(open(s).readlines())
            if content:
                # default is utf-8
                # todo: chardet, change to utf-8
                content = ''.join(content)
                try:
                    open(os.path.join(self.dest, filename), 'w').write(content)
                    logger.info('generate: %s' % os.path.join(self.dest, filename))
                    try:
                        if self.zip == 'yui':
                            os.popen('java -jar %s/yuicompressor.jar --charset UTF-8 %s -o %s' % (os.path.dirname(os.path.abspath(__file__)), os.path.join(self.dest, filename), os.path.join(self.dest, filenameM)))
                        elif self.zip == 'closure':
                            os.popen('java -jar %s/closure-compiler.jar --charset UTF-8 --js %s --js_output_file %s' % (os.path.dirname(os.path.abspath(__file__)), os.path.join(self.dest, filename), os.path.join(self.dest, filenameM)))
                    except Exception,e:
                        logger.error('%s' % e )
                except  Exception,e:
                    logger.error('%s' % e )


def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    
    parser = OptionParser(usage="""%s [options] xxx""" % __file__)
    parser.add_option('-c', '--config', help='config file', default='config.txt')
    parser.add_option('-z', '--zip', help='yui or closure, none is not zip')
    parser.add_option('-d', '--dest', help='dest dir', default='tmp')

    options, args = parser.parse_args(argv[1:])
    
    # get absolute path
    config, dest = os.path.abspath(options.config), os.path.abspath(options.dest)
    if not os.path.isfile(config):
        logger.error('config is not exists or not a file')
        sys.exit(-1)
    
    if not os.path.isdir(dest):
        os.makedirs(dest)
    
    try:
        builder = MyBuilder(config, dest, options.zip)
        builder.run()
    except Exception,e:
        logger.error('%s' % e)
    
    
if __name__ == '__main__':
    execute_from_command_line()
