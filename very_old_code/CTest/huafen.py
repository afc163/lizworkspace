#!/usr/bin/python
def Generate_Set_Partition(n=3):
    L = [0 for i in range(n)]
    T = [1 for i in range(n)]
    while(1):
        yield L
        i = n-1
        while L[i] == T[i]:
            i -= 1
        if i == 0:
            return
        L[i] += 1
        for j in range(i+1, n):
           L[j] = 0
           T[j] = L[i] + 1

N = 5
#A = [i for i in range(N)]
for l in Generate_Set_Partition(N):
    print l
    result = {}
    for i in range(N):
        if result.has_key(l[i]):
            result[l[i]].append(i)
        else:
            result[l[i]] = [i]
    print result.values()