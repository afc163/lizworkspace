//生成zipf分布随机数据
#include <iostream.h>
#include <stdlib.h>
#include <math.h>

const int R = 2000;  //数据元素, 有R个不同的频率
const double A = 0.75;  //定义参数A>1的浮点数
const double C = 1.0;  //这个C是不重要的,一般取1, 可以看到下面计算中分子分母可以约掉这个C

double pf[R]; //值为0~1之间, 是单个f(r)的累加值

void generate()
{
    double sum = 0.0;
    for (int i = 0; i < R; i++)
    {
        sum += C/pow((double)(i+2), A);  //位置为i的频率,一共有r个(即秩), 累加求和
    }
    
    for (int i = 0; i < R; i++)
    {
        if (i == 0)
            pf[i] = C/pow((double)(i+2), A)/sum;
        else
            pf[i] = pf[i-1] + C/pow((double)(i+2), A)/sum;
    }
    /*printf("%e     ", sum);
    for (int i = 0; i < R; i++)
    {
        printf("%e  ", pf[i]);
    }*/
}

void pick(int n)
{
    srand(time(00));
    //产生n个数
    for (int i = 0; i < n; i++)
    {
        int index = 0;
        double data = (double)rand()/RAND_MAX;
        while (data > pf[index])
            index++;
        printf("%d ", index);
    }
    printf("%s", "\n");
}

int main()
{
    generate();
    pick(1000);
    
    return 1;
}
