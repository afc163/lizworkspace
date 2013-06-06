ll = [1,1,0,1,0,1]

result = [ll[0]]
f = ll[0]
for i in ll:
    if f <> i:
        f = i
        result.append(i)
print result

result = [ ll[i] for i in range(len(ll)-1) if ll[i]<>ll[i+1] ]
result.append(ll[-1])
print result

result = []
for i in range(len(ll)-1):
    if ll[i]<>ll[i+1]:
        result.append(ll[i])
result.append(ll[-1])
print result