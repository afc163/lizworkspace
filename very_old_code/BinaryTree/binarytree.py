#!C:\\Python25
#-*- coding:utf-8 -*-
# Filename : binarytree.py
# Author: Sunlianglei@gmail.com
# Date: 2008/11/26
# function: construct a binary tree which stores the node using a dict,and 
#    implenment the arithmetic of traversal(preorder,inorder,postorder,level). 
# command [options] options: somedirs or current dir

try:
    import cPickle as pickle
except:
    import pickle
import sys
import os
from optparse import OptionParser

class BinaryTree(object):
    def __init__(self):
        self.root = None
        self.node = {}
        
    def ctree(self):       
        """构造一棵二叉树
        """
        ch = raw_input('enter :')
        if ch:
            if not self.root:
                self.root = ch
            self.node[ch] = [self.ctree(),self.ctree()]
            return ch
        else:
            return ''
    
    def __preorder(self, root):
        """
        """
        print root,
        if self.node[root][0]:                
            self.__preorder(self.node[root][0])
        if self.node[self.root][1]:                
            self.__preorder(self.node[root][1])
        
    def preorder(self):
        self.__preorder(self.root)
    
    def __inorder(self, root):       
        if self.node[root][0]:                
            self.__inorder(self.node[root][0])
        print root,
        if self.node[root][1]:                
            self.__inorder(self.node[root][1])
    
    def inorder(self):
        self.__inorder(self.root)

    def __postorder(self, root):
        if self.node[root][0]:                
            self.__postorder(self.node[root][0])      
        if self.node[self.root][1]:                
            self.__postorder(self.node[root][1])
        print root,
        
    def postorder(self):
        self.__postorder(self.root)

    def level(self):
        """二叉树的层次遍历
        使用一个队列辅助...
        """
        queue = [] 
        print self.root
        queue.append(self.root)
        try:        
            while queue:
                v = queue[0]
                del queue[0]
                for w in self.node[v]:
                    if w:
                        print w
                        queue.append(w)
        except IndexError:
            sys.exit(0) 
    
    def __breadth_first(self, tree,children=iter):##X
        """Traverse the nodes of a tree in breadth-first order.
        The first argument should be the tree root; children
        should be a function taking as argument a tree node and
        returning an iterator of the node's children.
        """
        #yield tree
        last = tree
        for node in self.__breadth_first(tree,children):
            for child in children(node):
                print child
                last = child
            if last == node:
                return
                
    def breadth_first(self):
        self.__breadth_first(self.root)
    
    def __str__(self):
        return "root: %s\n node:%s\n" % (self.root, self.node)

def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
        
    parser = OptionParser(usage="""binarytree.py [options] [filename]""")
    parser.add_option('-f', '--file', help='Give a File Name which stores a tree, Default: ./tree.data',default='./tree.data')
    parser.add_option('-c', '--create', help=' Give a FileName to Creat a Tree')
    parser.add_option('-p', '--preorder', help=' Output the result of preorder traversal of the given tree',action='store_true',default=False)
    parser.add_option('-i', '--inorder', help=' Output the result of inorder traversal of the given tree',action='store_true',default=False)
    parser.add_option('-a', '--postorder', help=' Output the result of postorder traversal of the given tree',action='store_true',default=False)
    parser.add_option('-l', '--levelorder', help=' Output the result of levelorder traversal of the given tree',action='store_true',default=False)
    
    options, args = parser.parse_args(argv[1:])
   
    if options.create:
        newfile = os.path.abspath(options.create)
        f = file(newfile, 'w')
        bt = BinaryTree()
        bt.ctree()
        pickle.dump(bt, f)
        f.close()
        print 'you have created a tree in' + newfile
        print bt
    else:
        dictfile = os.path.abspath(options.file)           
        
        if os.path.exists(dictfile):
            bt = pickle.load(open(dictfile))
        else:
            # the file is not exist
            print 'File: %s is not Exist' % dictfile
            sys.exit(0)
            
    # 测试各种遍历
    if options.preorder:
            print 'the result of preorder traversal:'                    
            bt.preorder()
            
    if options.inorder:
            print '\nthe result of inorder traversal:'
            bt.inorder()
            
    if options.postorder:
            print '\nthe result of postorder traversal:'
            bt.postorder()
            
    if options.levelorder:
            print '\nthe result of level traversal:'
            bt.level()
        
if __name__ == "__main__":
    execute_from_command_line()
