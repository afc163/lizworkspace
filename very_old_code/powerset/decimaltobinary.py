#!/usr/bin/env python
#Convert a number in the decimal system to the binary system
#file: decimaltobinary.py

def converter(i, num_length):
        b = ''
        while i > 0:
                j = i & 1
                b = str(j) + b
                i >>=  1
        bin_list = [ int(x) for x in str(b)]
        reqd_bin_list = [0] * ( num_length - len(bin_list) ) + bin_list
        return reqd_bin_list
