def A(k, x1, x2, x3, x4, x5):
    def B():
        k -= 1
        B = A = A(k, B, x1, x2, x3, x4)
    if k <= 0:
        A = x4 + x5
    else:
        B()

print A(10, 1, -1, -1, 1, 0)
