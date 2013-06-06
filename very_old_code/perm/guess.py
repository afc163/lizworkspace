def perm(l):
    if len(l) <= 1:
        yield l
    else:
        for i in l:
            for r in perm([j for j in l if j != i]):
                yield [i]+r

def comb(l, n):
    if n==0:
        yield []
    else:
        for i in range(len(l)-n+1):
            for r in comb(l[i+1:], n-1):
                yield [l[i]]+r

def compare(n,m):
    a = sum(n[i]==m[i] for i in range(len(n)))
    b = sum(1 for i in n if i in m)
    return a,b

def guess(n):
    answer = []
    for c in comb(range(10),4):
        for g,a,b in answer:
            if compare(c,g)[1] != b:
                break
        else:
            for p in perm(c):
                if p[0] == 0:continue
                for g,a,b in answer:
                    if compare(p,g) != (a,b):
                        break
                else:
                    yield p
                    a,b = compare(p,n)
                    answer.append((p,a,b))
                    if answer[-1][1] == 4:
                        return

print list(guess([6,8,9,7]))

for p in sum([list(perm(p)) for p in comb(range(10),4)], []):
    if p[0] == 0: continue
    answer = list(guess(p))
    assert answer[-1] == p
    if len(answer)>7:
        print len(answer),p