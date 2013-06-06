#!/usr/bin/python
"""产生1000000个随机整数,并保存到randomdata中
"""

import random
import array

a = array.array("i")
fp = open("randomdata", "w")
for i in xrange(1000000):
    a.append(random.randint(0,2147483648))
    if len(a) >= 1000:
        a.tofile(fp)
        fp.flush()
        del a[:]
fp.close()
