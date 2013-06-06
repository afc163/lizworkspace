#include <stdio.h>
#include <math.h>
#include <time.h>

int N = 30;
int Xmin = -100;
int Xmax = 100;

long f(void)
{
    long result = 0;
    int i;
    time_t t;
    int x;
    srand((unsigned) time(&t));
    
    for(i=0; i<N; i++)
    {
        //随机产生一个Xmin和Xmax之间的一个整数
        x = rand() % (Xmax - Xmin +1) + Xmin;
        printf("Random %d\n", x);
        result += x*x;
    }
    return result;
}

int main(void)
{
    printf("%d\n", f());
    return 0;
}
