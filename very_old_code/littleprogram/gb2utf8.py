# -*- encoding:utf-8 -*-
"""

对某个目录下的所有文件改变编码为utf8
Usage:
python gb2utf8.py somedir
"""

import os
import sys
import chardet

def change(filename):
    """改变filename的文件编码为utf8
    """
    fp = open(filename)
    content = fp.read()
    fp.close()
    codedetect = chardet.detect(content)["encoding"]
    if codedetect <> 'utf-8':                                   #是否是utf-8
        try:
            content = unicode(content, codedetect)                    #不是的话，则尝试转换
            content = content.encode('utf-8')
        except:
            print filename, ": bad unicode encode try!"
            return 0
    open(filename, 'w').write(content)
    print filename
        
    
if __name__ == "__main__" :

    if len(sys.argv) > 1:
        try:
            desdir = sys.argv[1]
        except:
            desdir = "."
    for dirpath,dirnames,filenames in os.walk(desdir):
        for filename in filenames:
            filename = os.path.join(dirpath,filename)
            ftype = filename[filename.rfind('.'):].lower().strip()
            if ftype not in [".gif", ".png", ".jpg", ".swf"]:
                change(filename)
