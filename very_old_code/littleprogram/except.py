#!/usr/bin/env python
# -*- coding: UTF-8 -*-
def getinfo():
	try:
		f = file('ab')
		s = raw_input('Enter something --> ')
		return s
	except:
		raise
	finally:
		if f is None:
			pass
		else:
			f.close()
		
def main():
    try:
        info = getinfo()
    except IOError, e:
        print 'IOError'
    else:
        if info is None:
            print 'info is None'
        else:
            print 'info is not None'

if __name__ == '__main__':
    main()

def search(searchname):
    statment = "select * from files_table where name REGEXP %s"
    paramter = ('.*/.*'+searchname+'[^/]*\\.sh$')
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:
            return {"searchname":searchname, "result":"no"}
        try:
            dot_filepath = os.getcwd()+'/dot/'+searchname+'.dot'
            dot_file = file(dot_filepath, 'wa')
            node_string = ''
            edge_string = ''
            all_files = {}
            all_list = list(excute.result)
            while all_list:
                item = all_list[0]
                del all_list[0]
                all_files[item] = []
                statment = "select files_table.id, files_table.name, files_table.type, files_table.info, %s_children.info from %s_children, files_table where %s_children.id = files_table.id"
                paramter = (item[0], item[0], item[0],)
                try:
                    excute.do_excute(statment, paramter)
                except:
                    continue
                for (child_id, child_name, child_type, child_info1, child_info2) in excute.result:
                    all_files[item].append((child_id, child_name, child_type, child_info1))   
                    edge_string += 'f%d -> f%d;\n' % (item[0], child_id)                      ###add new edge
                    if child_type == 'BASH' and 'exist:True;' in child_info1 and 'f%d ->' % child_id not in edge_string and (child_id, child_name, child_type, child_info1) not in all_list:
                        all_list.append((child_id, child_name, child_type, child_info1))    #recursive
                
            for (k, i) in all_files.items():
                node_string += 'f%d[label = "%d: %s"];\n' % (k[0], k[0], k[1][k[1].rfind('/')+1:])
        
                for o in i:
                    if 'f%d[' % o[0] in node_string:
                        continue
                    node_string += 'f%d[label = "%d: %s"];\n' % (o[0], o[0], o[1][o[1].rfind('/')+1:])
        
            dot_file.write('digraph G {\n%s%s}' % (node_string, edge_string))

            if os.system("dot -Tpng "+dot_filepath+" -o "+os.getcwd()+"/media/img/"+searchname+".png") == 0:
                print "dot -Tpng "+dot_filepath+" -o "+os.getcwd()+"/media/img/"+searchname+".png"
                return {"searchname":searchname, "pic":searchname+".png", "result":all_files}
            else:
                raise
        except IOError, e:
            raise
        finally:
            dot_file.close()
    except Exception, e:
        raise
    finally:
        excute.close_connect()

