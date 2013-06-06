import stackless
def print_x(x):
    print "1:", x
    stackless.schedule()
    print "2:", x
    stackless.schedule()
    print "3:", x
    stackless.schedule()

    
if __name__ == "__main__" :
    stackless.tasklet(print_x)("one")
    stackless.tasklet(print_x)("two")
    stackless.tasklet(print_x)("three")
    stackless.run()
