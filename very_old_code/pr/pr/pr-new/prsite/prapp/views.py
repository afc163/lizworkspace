# Create your views here.
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
import os
from myDB import MysqlExcuter

def show(request, type=0):
    #filetype = request.POST.get("filetype", None)
    #if filetype:
    #    filetype = 'S'
    
    if type == 1:
        return render_to_response("program.html")
    elif type == 2:
        return render_to_response("configure.html")
    elif type == 3:
        return render_to_response("machine.html")
    elif type == 4:
        return render_to_response("path.html")
    return render_to_response("script.html")

def search_script(searchname):
    statment = "select * from files_table where type='BASH' and name like %s"####
    paramter = ('%'+searchname+'%')
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:  #don't have searchname
            return {"searchname":searchname, "result":"no"}
        try:
            dot_filepath = os.getcwd()+'/dot/'+searchname+'.dot'    #in dic dot/
            dot_file = file(dot_filepath, 'wa')
            node_string = ''
            edge_string = ''
            all_files = {}
            all_list = []
            for temp in excute.result:
                temp_name = temp[1][temp[1].rfind('/')+1:]
                if searchname in temp_name:
                    all_list.append(temp)
            while all_list:
                item = all_list[0]#(id, name, type, info)
                del all_list[0]
                all_files[item] = []
                statment = "select files_table.id, files_table.name, files_table.type, files_table.info, %s_children.info from %s_children, files_table where %s_children.id = files_table.id"
                paramter = (item[0], item[0], item[0],)
                try:
                    excute.do_excute(statment, paramter)    #select this bash file's table
                except:                                     #some shell script'tables does not exist
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
def search_program(searchname):#type='C' or type='PYTHON' or type='PERL' and 
    statment = "select * from files_table where name like %s"###
    paramter = ('%'+searchname+'%')
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:  #don't have searchname
            return {"searchname":searchname, "result":"no"}
        all_list = []
        for temp in excute.result:
            temp_name = temp[1][temp[1].rfind('/')+1:]
            if searchname in temp_name and temp[2] in ['C', 'PYTHON', 'PERL']:
                all_list.append(temp)        
        return {"searchname":searchname, "result":all_list}
    except Exception, e:
        raise
    finally:
        excute.close_connect()
def search_configure(searchname):
    statment = "select * from files_table where type='CONF' and name like %s"
    paramter = ('%'+searchname+'%')
    
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:  #don't have searchname
            return render_to_response("show.html", {"searchname":searchname, "result":"no"})
        #get script machine path
        conf_files = []
        for temp in excute.result:
            temp_name = temp[1][temp[1].rfind('/')+1:]
            if searchname in temp_name:
                conf_files.append(temp)        
        
        #path = []
        #machine = []
        fin_result = {}
        statment = "select * from files_table where type='BASH' and info like %s"
        paramter = ('%exist:True;%')
        excute.do_excute(statment, paramter)
        all_script = excute.result
        for (id, name, type, info) in conf_files:
            script_files = []
            for (sc_id, sc_name, sc_type, sc_info) in all_script:
                statment = "select * from %s_children where id=%s"
                paramter = (sc_id, id)
                excute.do_excute(statment, paramter)
                if excute.result:
                    script_files.append((sc_id, sc_name, sc_type, sc_info))
            #temp_path = name[:name.rfind('/')]
            #if temp_path not in path:
            #    path.append(temp_path)
            #host_index = info.find('host:')
            #if host_index != -1:
            #    temp_machine = info[host_index+5:info[host_index+5:].find(';')]
            #    if temp_machine not in machine:
            #        machine.append(temp_machine)
            fin_result[(id, name, type, info)] = script_files
        return {"searchname":searchname, "result":fin_result}
    except Exception, e:
        raise
    finally:
        excute.close_connect()        
def search_machine(searchname):
    statment = "select * from files_table where info like %s"###
    paramter = ('%host:'+searchname+'%')
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:  #don't have searchname
            return {"searchname":searchname, "result":"no"}
        machine_path = {}
        for (id, name, type, info) in excute.result:
            host_index = info.find('host:')
            machine = info[host_index+5:host_index+5+info[host_index+5:].find(';')]
            temp_path = name[:name.rfind('/')+1]
            if machine_path.has_key(machine):
                if temp_path not in machine_path[machine]:
                    machine_path[machine].append(temp_path)
            else:
                machine_path[machine] = [temp_path]
        return {"searchname":searchname, "result":machine_path}
    except Exception, e:
        raise
    finally:
        excute.close_connect()
def search_path(searchname):
    statment = "select * from files_table where name like %s"
    paramter = ('%'+searchname+'%')
        
    try:
        excute = MysqlExcuter()
        excute.do_excute(statment, paramter)
        if not excute.result:  #don't have searchname
            return render_to_response("show.html", {"searchname":searchname, "result":"no"})
        #get script machine path
        all_path = []
        for temp in excute.result:
            temp_name = temp[1][:temp[1].rfind('/')]
            if searchname in temp_name:
                all_path.append(temp)        
        path_scma = {}
        for (id, name, type, info) in all_path:
            machine = ''
            host_index = info.find('host:')
            if host_index != -1:
                machine = info[host_index+5:host_index+5+info[host_index+5:].find(';')]
            script = ''
            if type == 'BASH':
                script = name
            temp_path = name[:name.rfind('/')+1]
            print temp_path
            if path_scma.has_key(temp_path):
                if machine and machine not in path_scma[temp_path]['Machine']:
                    path_scma[temp_path]['Machine'].append(machine)
                if script and script not in path_scma[temp_path]['Script']:
                    path_scma[temp_path]['Script'].append(script)
            else:
                path_scma[temp_path] = {}
                path_scma[temp_path]['Machine'] = []
                path_scma[temp_path]['Script'] = []
                if machine:
                    path_scma[temp_path]['Machine'].append(machine)
                if script:
                    path_scma[temp_path]['Script'].append(script)
        print path_scma
        return {"searchname":searchname, "result":path_scma}
    except Exception, e:
        raise
    finally:
        excute.close_connect()
    
def start(request):
    searchname = request.POST.get("searchname", None)
    if searchname is None:
        return HttpResponseRedirect("/")
    searchname = searchname.strip()
    ####searchname filename.png if it has already exist
    if searchname == '':
        return HttpResponseRedirect("/")
    filetype = request.POST.get("filetype", None)
    try:
        if filetype == 'S':   #Script
            result_dic = search_script(searchname)
            result_dic['filetype'] = 'script'
            return render_to_response("script_result.html", result_dic)
        elif filetype == 'P': #Program
            result_dic = search_program(searchname)
            result_dic['filetype'] = 'program'
            return render_to_response("program_result.html", result_dic)
        elif filetype == 'C': #Configure
            result_dic = search_configure(searchname)
            result_dic['filetype'] = 'conf'
            return render_to_response("conf_result.html", result_dic)
        elif filetype == 'M': #Machine
            result_dic = search_machine(searchname)
            result_dic['filetype'] = 'machine'
            return render_to_response("machine_result.html", result_dic)
        elif filetype == 'p': #Path
            result_dic = search_path(searchname)
            result_dic['filetype'] = 'path'
            return render_to_response("path_result.html", result_dic)
        else:
            return HttpResponseRedirect("/")
    except Exception, e:
        #return HttpResponse("Searching Error: %s" % e)
        return render_to_response("error.html", {'errorinfo':'%s' % e})
    
