#!/usr/bin/env python
#Author: Shriphani Palakodety a.k.a PSP
#file: powerset.py

import decimaltobinary

def powerSetMaker(set_name):
        powerset = []
        for i in range(2 ** (len(set_name))):
                bin_list = decimaltobinary.converter(i, len(set_name))
                subset = []
                for n in range(len(bin_list)):
                        if bin_list[n] == 1:
                                subset.append(set_name[n])
                        else:
                                continue
                powerset.append(subset)
        return powerset
