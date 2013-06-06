#! /usr/bin/python
#  -*- coding: utf-8 -*-

class Graph():
    def __init__(self):
        self.node_edge_dic = {}
        self.node_info = {}
        self.node_id = 0

    def __str__(self):
        """
        get Graph's string
        """
        graph_string = 'the node inform:\n'
        for (id, node) in self.node_info.items():
            graph_string += '\n文件： %d, filename: %s\n' % (id, node)
        graph_string += '*'*20 + 'the edge inform:\n'
        for (id, node_edge) in self.node_edge_dic.items():
            if not len(node_edge):
                continue
            graph_string += '\n' + '-'*100 + '\n'
            graph_string += '\nshell脚本文件：%s -->\n' % self.node_info[id]
            for edge in node_edge:
                graph_string += '被调用文件类型：%s --> 文件名称：%s\t%s\n' % (edge.info, self.node_info[edge.to], edge.line_num)
        return graph_string
    
    def add_node(self, filename):
        """
        add a node to graph
        """
        new_node = Node(filename)
        self.node_info[self.node_id] = new_node
        
        self.node_edge_dic[self.node_id] = []
        self.node_id += 1
        
    def add_edge(self, parent, child, child_type, line_num):
        """
        add a edge to graph
        """ 
        parent_id = self.get_id(parent)
        child_id = self.get_id(child)
        #if not self.exists_edge(parent_id, child_id):
        #    self.node_info[parent_id].handled_sign = True
        #    new_edge = Edge(child_id, child_type, 1)
        #    self.node_edge_dic[parent_id].append(new_edge)
        #else:
        #    for i in self.node_edge_dic[parent_id]:
        #        if i.to == child_id:
        #            i.times += 1
        self.node_info[parent_id].handled_sign = True
        new_edge = Edge(child_id, child_type, line_num)
        self.node_edge_dic[parent_id].append(new_edge)
        
    def exists_edge(self, f, t):#X
        for i in self.node_edge_dic[f]:
            if i.to == t:
                return True
        return False
    def get_id(self, name):
        for i in self.node_info.keys():
            if self.node_info[i].name == name:
                return i
                
    def contains(self, filename):
        for one in range(self.node_id):
            if self.node_info[one].name == filename:
                return True
        return False
    
    def isHandled(self, filename):
        for one in range(self.node_id):
            if self.node_info[one].name == filename and not self.node_info[one].handled_sign:
                return False
        return True

class Node():
    def __init__(self, name):
        self.name = name
        self.handled_sign = False
        
    def __str__(self):
        return self.name
    
class Edge():
    edge_num = 0
    def __init__(self, to, info, line_num):
        Edge.edge_num = Edge.edge_num + 1
        self.id = Edge.edge_num
        self.to = to
        self.info = info
        self.line_num = line_num

    def __str__(self):
        pass

