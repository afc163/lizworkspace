#!/usr/bin/python
#coding=utf-8
import os
import sys
from optparse import OptionParser
from ID3 import *  # depend on ID3
import chardet

def get_usage():
    usage = """
        %prog [options] action [applist]:
        ...........
        """
    return usage

def do_main(argv=None):
    if argv is None:
        argv = sys.argv
    
    parser = OptionParser(usage=get_usage())
    parser.add_option("-d", "--directory",
                        dest="mp3_directory",
                        action="store",
                        type="string",
                        default="./temp/",
                        help="the MP3 directory",
                        )
    options, reminder = parser.parse_args()
    abs_path = os.path.abspath(options.mp3_directory)
    if not os.path.isdir(abs_path):
        print '%s is not exist' % abs_path
        return -1
    singer_song_dic = dict()
    for root, dirs, files in os.walk(abs_path):
        print 'In directory %s' % root
        for onefile in files:
            singer = 'other_format'
            if onefile.endswith('.mp3') or onefile.endswith('.MP3'):
                #one song get information
                #one = MP3(root, onefile)##?
                singer = 'other_mp3'

                try:
                    one = ID3(os.path.join(root, onefile))
                    print one
                    if one.has_key('ARTIST'):
                        singer = one['ARTIST']
                        codedetect = chardet.detect(singer)["encoding"]
                        try:
                            singer = unicode(singer, codedetect)
                            singer = singer.encode('utf8')
                        except:
                            singer = onefile[:-4]
                        print singer
                except InvalidTagError, message:
                    print "Invalid ID3 tag:", message
                    continue
            if singer_song_dic.has_key(singer):
                singer_song_dic[singer].append((root, onefile))
            else:
                singer_song_dic[singer] = [(root, onefile)]
    #print singer_song_dic

    #one method is create new tmpdir and move files in and rename this dir in the end
    #mkdir tmpdir
    #mkdir onesinger
    #mv source onesinger/
    #rm -R abs_path
    #mv tmpdir abs_path
    #other method is in original dir, consider some dir's exisitence

    #adjust all files
    flag = raw_input('continue y or n:')
    if flag == 'n':
        return 0
    for singer in singer_song_dic.keys():
        singer_dir = ''
        if len(singer_song_dic[singer]) < 2:
            singer_dir = os.path.join(abs_path, 'other')
        else:
            singer_dir = os.path.join(abs_path, singer)
        if not os.path.exists(singer_dir):
            #create new path
            if not os.system('mkdir  "' + singer_dir + '"'):
                print 'mkdir %s successfully' % singer_dir
            else:
                print 'mkdir %s failed' % singer_dir
                continue
        #move in new path
        for (path, filename) in singer_song_dic[singer]:
            source_path = os.path.join(path, filename)
            dest_path = singer_dir#os.path.join(singer_dir, filename)
            if path == dest_path:
                continue
            if os.path.exists(os.path.join(singer_dir, filename)):
                #rm source_path?   #the same singer  and same filename????????? and also rm one
                print '%s is exist in %s, so dont move in' % (source_path, os.path.join(singer_dir, filename))
                continue
            #mv source_path dest_path----------change to cp, md5sum, rm?
            if not os.system('mv "%s" "%s"' % (source_path, dest_path)):
                print 'mv %s %s successfully' % (source_path, dest_path)
                #one.set_path(singer_dir)
            else:
                print 'mv %s %s failed' % (source_path, dest_path)

if __name__ == '__main__':
    do_main()
    print 'Over'
