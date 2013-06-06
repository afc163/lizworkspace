#!/usr/bin/env python
#coding=utf-8

import sys
import re
import os
from optparse import OptionParser

class CrontabAnalysis:
    ptNot = r'^[ #\n]+'
    ptHave = r'\.(sh|c)'
    ptFileName = r'\s(\./)?\w+\.(sh|c)\b'
    ptPath = r'\scd.*;\s'
    ptPathName = r'\s~?/\S+\b'

    def __init__(self, cFile):
        '''init'''
        self.cFile = cFile
        self.resultList = []

    def doAnalyze(self):
        '''Analaze %s and get list''' % self.cFile
        while True:
            oneline = self.cFile.readline()     # add try except
            if len(oneline) == 0:
                break
            else:
                if  re.search(CrontabAnalysis.ptNot, oneline):
                    #print 'out: %s' % oneline
                    continue
                else:
                    #print 'want: %s' % oneline
                    self.analyzeOne(oneline)

    def analyzeOne(self, aline):
        try:
            if re.search(CrontabAnalysis.ptHave, aline) != None:   # have .sh or .c可以省略
                fileName = re.search(CrontabAnalysis.ptFileName, aline).group().strip()#可以用findall or finditer
                path = re.search(CrontabAnalysis.ptPath, aline).group().strip()
                filePath = re.search(CrontabAnalysis.ptPathName, path).group().strip()
                if fileName.startswith('.'):            #./someFile
                    fileName = fileName[2:]
                if filePath.startswith('~'):            #~/somePath
                    homeDir = os.getenv('HOME')
                    filePath = homeDir + '/' + filePath[2:]
                #print "/".join((filePath, fileName))
                self.resultList.append("/".join((filePath, fileName)))
        except:
            print "ERROR IN RE"

    def __str__(self):
        #resultString = ""
        #num = 0
        #for one in self.resultList:
        #    resultString += "%d:\t%s\n" % (num++, one)
        #return resultString
        return '\n'.join(self.resultList)


def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    # Parse the command-line arguments.
    parser = OptionParser(usage="""cront_filter.py [options] file""")
    #parser.add_option('--version', help='Show Version Information')
    parser.add_option('-f', '--file', help='Give the File Name, Default: ./crontab',default='./crontab')
    #........

    options, args = parser.parse_args(argv[1:])

    fullName = os.path.abspath(options.file)
    print 'the FullName is: %s\n' % fullName
    if os.path.exists(fullName):
        # doAnalyze
        crontabFile = file(fullName, 'r')
        CA = CrontabAnalysis(crontabFile)
        CA.doAnalyze()
        crontabFile.close()
        print CA
        # save to file
        resultFile = file('CrontabAnalysis_result', 'w')
        resultFile.write(CA.__str__())
        resultFile.close()
    else:
        # the file is not exist
        print 'File: %s is not Exist' % fullName
        sys.exit(0)

# Start Here
if __name__ == "__main__":
    execute_from_command_line()

    print '\nDone\n'