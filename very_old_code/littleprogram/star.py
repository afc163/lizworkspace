#!/usr/bin/python
#coding:utf-8

import random
import math

def star(A, B, C, D, E):
    slist=[0 for i in range(11)]
    kac = (C[1]-A[1])/(C[0]-A[0])
    bac = (C[0]*A[1]-C[1]*A[0])/(C[0]-A[0])
    kad = (D[1]-A[1])/(D[0]-A[0])
    bad = (D[0]*A[1]-D[1]*A[0])/(D[0]-A[0])
    kbe = (E[1]-B[1])/(E[0]-B[0])
    bbe = (E[0]*B[1]-E[1]*B[0])/(E[0]-B[0])
    kbd = (D[1]-B[1])/(D[0]-B[0])
    bbd = (D[0]*B[1]-D[1]*B[0])/(D[0]-B[0])
    kce = (E[1]-C[1])/(E[0]-C[0])
    bce = (E[0]*C[1]-E[1]*C[0])/(E[0]-C[0])

    def yac(x):
        return kac*x+bac
    def yad(x):
        return kad*x+bad
    def ybe(x):
        return kbe*x+bbe
    def ybd(x):
        return kbd*x+bbd
    def yce(x):
        return kce*x+bce
    
    def isLeft(k, b, x, y):
        """判断点(x, y)是否在直线y=kx+b的左边
        """
        yy = k*x+b
        if k >= 0 and y >= yy or k < 0 and y <= yy:
            return True
        return False
        
    def isRight(k, b, x, y):
        """判断点(x, y)是否在直线y=kx+b的右边
        """
        yy = k*x+b
        if k >= 0 and y <= yy or k < 0 and y >= yy:
            return True
        return False
        
    for i in range(1000000):
        x = random.uniform(-1, 1)
        y = random.uniform(-(math.sqrt(1-x**2)), (math.sqrt(1-x**2)))
        if isLeft(kac, bac, x, y) and isRight(kbe, bbe, x, y):  #属于区域S1
            slist[0] += 1
        elif isLeft(kbd, bbd, x, y) and isLeft(kac, bac, x, y):  #属于区域S2
            slist[1] += 1
        elif isLeft(kbd, bbd, x, y) and isRight(kce, bce, x, y):  #属于区域S3
            slist[2] += 1
        elif isRight(kad, bad, x, y) and isRight(kce, bce, x, y):  #属于区域S4
            slist[3] += 1
        elif isRight(kad, bad, x, y) and isUpper(kbe, bbe, x, y):  #属于区域S5
            slist[4] += 1
            continue
        if y < yad(x) and y >yac(x) and y > ybe(x):  #属于区域S6
            slist[5] += 1
            continue

        if y > ybd(x) and y < ybe(x) and y < yac(x):  #属于区域S7
            slist[6] += 1
            continue
        if y < ybd(x) and y > yac(x) and y > yce(x):  #属于区域S8
            slist[7] += 1
            continue
        
        if y > ybd(x) and y < yce(x) and y < yad(x):  #属于区域S9
            slist[8] += 1
            continue
        if y < ybe(x) and y > yad(x) and y > yce(x):  #属于区域S10
            slist[9] += 1
        else:   #属于区域S11
            slist[10] += 1
    print slist
    
    #for i  in slist:
    #    #print i
    #    i = i/20000*math.pi
    
    
    #ave = (s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11)/11.0
    #s = (s1-ave)**2 + (s2-ave)**2 +(s3-ave)**2 + (s4-ave)**2 +(s5-ave)**2 +(s6-ave)**2 +(s7-ave)**2 +(s8-ave)**2 +(s9-ave)**2 +(s10-ave)**2 +(s11-ave)**2 
    #print s
    #print ave
            
if __name__ == "__main__":
    star((-(2**0.5/2.0),(2**0.5/2.0)), (-(3**0.5/2.0),-1/2.0), (0,-1), (1,0), ((2**0.5/2.0),(2**0.5/2.0)))
    
    
    
    
    
    
    