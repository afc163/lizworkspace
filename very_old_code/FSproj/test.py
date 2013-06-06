#!/usr/bin/python

def generate(a, b, N, Qlist):
    count = 0
    for i in Qlist:
        if not ((i % a) and (i % b)):
            print i,
            count += 1
            if count >= N:
                break

def rearrange(somestring):
    head = 0
    tail = len(somestring)-1
    while head < tail:
        while head < tail and somestring[head].islower():
            head += 1
        while head < tail and somestring[tail].isupper():
            tail -= 1
        tmp = somestring[head]
        somestring[head] = somestring[tail]
        somestring[tail] = tmp
        head += 1
        tail -= 1
    print ''.join(somestring)
    
        
if __name__ == "__main__":
    #generate(3, 5, 6, range(1, 20))
    rearrange(list('HiPythonABc'))
    print