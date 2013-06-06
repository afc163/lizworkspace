#!/usr/bin/python
#coding=utf-8

#description : 
#一个三种类型的混合的字符串，按照类型，把他们依次归类，三种类型分别放在一个字符串的前面，中间，后面
#时间复杂度为O(n)，空间复杂度为O(1)

def do_class(origion_list=[]):
    '''classify the string according to three types
    
    for example:
    origion_string : ABCABCAB
    result_string : AAABBBCC
    '''
    a = 0
    c = len(origion_list)-1
    while a <= c and origion_list[a] == 'A':    #find the first a elemnt is not 'A'
        a += 1
    b_start = a                                 #find the address b: maybe or not
    b_end = -1
    while c >= 0 and origion_list[c] == 'C':    #find the first a elemnt is not 'C'
        c -= 1
    i = a
    while i <= c:
        if origion_list[i] == 'B':
            if b_end == -1:                     #set 'B' start and end position
                b_start = i
                b_end = i
                while b_end <= c and origion_list[b_end] == 'B':
                    b_end += 1
                i = b_end                       #b_end not include 'B'
            else:
                b_end = i+1
                i += 1
        elif origion_list[i] == 'C':            #switch current element and c
            temp = origion_list[i]
            origion_list[i] = origion_list[c]
            origion_list[c] = temp
            c -= 1
            while c > b_end and origion_list[c] == 'C': #reset the c position
                c -= 1
        elif origion_list[i] == 'A':            #switch current element and b_start
            temp = origion_list[i]
            origion_list[i] = origion_list[b_start]
            origion_list[b_start] = temp
            b_start += 1
            b_end = i+1
            i = b_end
        else:
            i += 1
            pass
    
    return ''.join(origion_list)
    
if __name__ == '__main__':
    while 1:
        origion_string = raw_input('please input the string with three type:')
        result_string = do_class(list(origion_string))
        print 'the result is : ', result_string
