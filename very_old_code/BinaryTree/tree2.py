#coding=utf-8


## 头部加上解释器路径, 文档说明, 作者, 简单描述此脚本功能
## 几点扩展:
#1) 注释的好看些
#2) 去掉brlist, 构成一个BinaryTree类
#3) 既然二叉树有先序, 中序, 后序, 层次, 而不是深先, 广先, 那么把他们都实现下,,作为BinaryTree的方法
#4) main中使用OptionParser构成, 可以根据命令行参数进行不同的执行, 比如说运行python tree.py -t firstorder, 则输出的是二叉树的先序遍历结果
#5) ...

def tree(br,list):#br是一字典，list用于存储所有节点，以便取出根结点，这里相当浪费
    '''构造一棵二叉树，输入要求是空结点（包括叶子结点下的空结点）直接按回车输入 ##建议使用标准docstring书写
    '''
    ch = raw_input('enter :')    ## 的确, list是浪费了, 可以直接保存root的,也就是第一次输入的字符
    list.append(ch) 
    if ch:
        br[ch] = [tree(br,list),tree(br,list)]
        return ch
    else:
        return ''
    
def depth_first(brlist, br):  #深度优先算法，brlist是一包含根结点的列表［'root',''］      
    if brlist[0]:                ## 其实这里的brlist直接可以指定为树/子树的根, 也就是br的某个key值
        print brlist[0],
        depth_first(br[brlist[0]],br)#递归调用

    if brlist[1]:    
        print brlist[1],
        depth_first(br[brlist[1]],br)
    else:
        return  # else可以去掉


        
def breadth_first(brlist, br):#广度优先     就是层次遍历
    queue = [] #建立一个队列，用于存放根结点
    print brlist[0],
    queue.append(brlist[0])
    try:        
        while queue:
            v = queue[0]
            del queue[0]#出队列
            for w in br[v]:#依次取出子结点中的非空结点，并将其进队列
                if w:
                    print w, 
                    queue.append(w)
    except IndexError:
        import sys
        sys.exit(0)            
           
        
if __name__ == "__main__":
    try:
        import cPickle as pickle# import部分放到脚本首部
    except:
        import pickle
        
    try:                   
        br = pickle.load(open('tree.data'))
    except:
        try:
            br = {}
            list = []
            tree(br,list)               
            br['root'] = [list[0],'']#将根加入字典
            pickle.dump(br, open('tree.data', 'w')) #将字典pickle到文件          
        except IndexError:
            import sys
            sys.exit(0) 
                      
    print 'tree is',br    
    print 'root is %s' %(br['root'][0])
        
    print 'the result of depth_first:'    
    depth_first(br['root'],br)
    print '\nthe result of breadth_first:'    
    breadth_first(br['root'],br)
