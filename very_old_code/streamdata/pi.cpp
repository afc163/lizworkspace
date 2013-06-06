//使用Monte Carlo计算pi值
#include <iostream.h>
#include <time.h>
#include <iomanip.h>

const long N=2000000000;  //定义随机点数

int main()
{
    int n = 0;      //统计落入1/4单位圆内的点数
    double x, y;  //坐标
    
    srand(time(00));
    
    for (int i = 1; i <= N; i++)
    {
        x = (double)rand()/RAND_MAX; //随机产生x,y坐标,在0~1之间
        y = (double)rand()/RAND_MAX;
        
        if (x*x + y*y <= 1.0) n++;
    }
    
    cout<<"The PI is "<<setprecision(15)<<4*(double)n/N<<endl;
    return 1;
}
