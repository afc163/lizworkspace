def perm(items, n=None):
    if n is None:
        n = len(items)
    for i in range(len(items)):
        v = items[i:i+1]
        if n == 1:
            yield v
        else:
            rest = items[:i] + items[i+1:]
            for p in perm(rest, n-1):
                yield v + p

n = 0
for i in perm([0, 1, 2, 3, 4, 5, 6, 7, 8, 9], 4):
    n += 1
print n