#! /usr/bin/python
#  -*- coding: utf-8 -*-

class Graph():
    def __init__(self):
        self.node_edge_dic = {}
        self.node_info = {}

    def __str__(self):
        """
        get Graph's string
        """
        graph_string = 'the node inform:\n'
        for (filename, filenode) in self.node_info.items():
            graph_string += '\n文件： %s, filename: %s\n' % (filename, filenode)
        graph_string += '*'*20 + 'the edge inform:\n'
        for (fileid, node_edge) in self.node_edge_dic.items():
            if not len(node_edge):
                continue
            graph_string += '\nshell脚本文件：%s -->\n' % self.id_to_filename(fileid)
            for edge in node_edge:
                graph_string += '被调用文件类型：%s --> 文件名称：%s\n' % (edge.info, self.id_to_filename(edge.to))
        return graph_string
    
    def add_node(self, filename):
        """
        add a node to graph___filename: file_info
        """
        new_node = Node()
        self.node_info[filename] = new_node
        
        self.node_edge_dic[filename] = []
        
    def add_edge(self, parent, child, child_type):
        """
        add a edge to graph
        """ 
        self.node_info[parent].handled_sign = True
        new_edge = Edge(child.id, child_type)
        self.node_edge_dic[parent.id].append(new_edge)
    
    def id_to_filename(self, id):
        for i in self.node_info.keys():
            if self.node_info[i].id == id:
                return i
        
    def get_id(self, name):
        index = -1
        for i in range(len(self.node_info)):
            if self.node_info[i].name == name:
                index = i
                break
        if index != -1:
            return self.node_info.keys()[index]
                
    def contains(self, filename):
        """
        contains: 判断此文件是否已经被包含
        """
        if filename in self.node_info.keys():
            return True
        return False
    
    def isHandled(self, filename):
        """
        isHandled: 判断此bash脚本是否已经处理过
        """
        if self.node_info[filename].handled_sign:
            return True
        return False

class Node():
    node_num = 0
    def __init__(self):
        Node.node_num = Node.node_num + 1
        self.id = Node.node_num
        self.md5 = 0
        self.handled_sign = False
        
    def __str__(self):
        return 'fileinfo: %d' % self.id
    
class Edge():
    edge_num = 0
    def __init__(self, to, info):
        Edge.edge_num = Edge.edge_num + 1
        self.id = Edge.edge_num
        self.to = to
        self.info = info

    def __str__(self):
        pass