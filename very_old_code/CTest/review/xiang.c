#include <stdio.h>

/*
>>> for i in xrange(0,81):
...     print i/9, i%9
也就是除和模，得到的数字和个数都是一样的。
*/
int get_AB(void){
    int i = 81;
    while (i--) {
        if ( i/9%3 == i%9%3 )
             continue;
        printf("A = %d, B = %d\n", i/9+1, i%9+1);
    }
    return 0;
}

struct {
    unsigned char a:4;
    unsigned char b:4;
} i;

int get_AB2(void){
    for (i.a=1; i.a<=9; i.a++)
        for (i.b=1; i.b<=9; i.b++)
            if (i.a%3 != i.b%3)
                printf("A = %d, B = %d\n", i.a, i.b);
}

int elevator(void){
    int N = 10; //总层数
    int nPerson[N]; // nPerson[i]表示到第i层的乘客数目
    int nMinFloor, nTargetFloor;
    int N1, N2, N3; //i层下, i层, i层上
    int i;
    for(i=1; i<=N; i++){
        nPerson[i] = i-1;
        printf("%d ", i-1);
    }
    
    nTargetFloor = 1;
    nMinFloor = 0;
    for (N1=0, N2=nPerson[1], N3=0, i=2; i<=N; i++){
        N3 += nPerson[i];
        nMinFloor += nPerson[i] * (i-1);
    }
    for (i=2; i<=N; i++){
        if (N1 + N2 < N3){
            nTargetFloor = i;
            nMinFloor += (N1 + N2 - N3);
            N1 += N2;
            N2 = nPerson[i];
            N3 -= nPerson[i];
        } else break;
    }
    printf("\n%d, %d\n", nTargetFloor, nMinFloor);
}
int main(void) {
    //get_AB2();
    elevator();
}

