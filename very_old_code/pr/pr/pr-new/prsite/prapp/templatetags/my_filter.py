from django import template
#from django.template import resolve_variable

register=template.Library()

def get_name(file):
    slash_index = file.rfind('/')+1
    if slash_index == 0:
        return file
    return file[:slash_index]+'<span id="filename">'+file[slash_index:]+'</span>'
def get_filename(file):
    slash_index = file.rfind('/')+1
    return file[slash_index:]
def get_path(file):
    slash_index = file.rfind('/')+1
    return file[:slash_index]
def get_pro_info(info):
    s = ''
    temp_info = info.split(';')
    for t in temp_info:
        index = t.find(':')
        if index == -1:
            break
        type = t[:index]
        info = '<span class="prvalue">'+t[index+1:]+'</span>'
        if type == 'host':
            s += '<p class="ftype">Machine-->'+info+'</p>'
        elif type == 'md5':
            s += '<p class="ftype">Md5 Value-->'+info+'</p>'
        elif type == 'info':
            s += '<p class="ftype">Other Info-->'+info+'</p>'
        else:
            pass
    return s
def get_conf_info(info):
    s = ''
    temp_info = info.split(';')
    for t in temp_info:
        index = t.find(':')
        if index == -1:
            break
        type = t[:index]
        info = '<p>'+t[index+1:]+'</p>'
        if type == 'host':
            s += '<p class="ftype">Machine<div class="oneline">'+info+'</div></p>'
        elif type == 'md5':
            s += '<p class="ftype">Md5 Value<div class="oneline">'+info+'</div></p>'
        elif type == 'info':
            s += '<p class="ftype">Other Info<div class="oneline">'+info+'</div></p>'
        elif type == 'exist':
            s += '<p class="ftype">Exist<div class="oneline">'+info+'</div></p>'
        else:
            pass
    return s
def get_inform(children_list):
    sc = []
    pr = []
    co = []
    pa = []
    ma = []
    ot = []
    for (id, name, type, info) in children_list:
        temp_path = name[:name.rfind('/')]
        if temp_path not in pa:
            pa.append(temp_path)
        temp_info = info.split(';')
        for t in temp_info:
            host_index = t.find('host:')
            if host_index != -1:
                temp_host = t[host_index+5:]
                if temp_host not in ma:
                    ma.append(temp_host)
                break
        if info:
            info = ' <span id="otherinfo">('+info+')</span>'
        if type == 'BASH':
            sc.append(str(id)+'-->'+get_name(name)+info)
        elif type == 'CONF':
            co.append(str(id)+'-->'+get_name(name)+info)
        elif type == 'C' or type == 'PYTHON' or type == 'PERL' :
            pr.append(str(id)+'-->'+get_name(name)+info)
        else:
            ot.append(str(id)+'-->'+get_name(name)+info)
    s = ''
    if sc:
        ss =  '</p><p>'.join(sc)
        s += '<p class="ftype">Script files</p><div class="oneline"><p>'+ss+'</p></div>'
    if pr:
        ss =  '</p><p>'.join(pr)
        s += '<p class="ftype">Program files</p><div class="oneline"><p>'+ss+'</p></div>'
    if co:
        ss =  '</p><p>'.join(co)
        s += '<p class="ftype">Configure files</p><div class="oneline"><p>'+ss+'</p></div>'
    if ot:
        ss =  '</p><p>'.join(ot)
        s += '<p class="ftype">Other files</p><div class="oneline"><p>'+ss+'</p></div>'
    if pa:
        ss =  '</p><p>'.join(pa)
        s += '<p class="ftype">Path</p><div class="oneline"><p>'+ss+'</p></div>'
    if ma:
        ss =  '</p><p>'.join(ma)
        s += '<p class="ftype">Machines</p><div class="oneline"><p>'+ss+'</p></div>'
    return s    
def show_key(name, key):
    return name.replace(key, '<span class="showkey">'+key+'</span>')
register.filter('get_inform', get_inform)
register.filter('get_name', get_name)
register.filter('get_filename', get_filename)
register.filter('get_path', get_path)
register.filter('get_pro_info', get_pro_info)
register.filter('get_conf_info', get_conf_info)
register.filter('show_key', show_key)


