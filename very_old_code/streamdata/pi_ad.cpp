//计算pi的更好方法,级数方法
#include <iostream.h>
#include <iomanip.h>

int main()
{
    double sum = 0.0, f = 1;
    
    for (int i = 1; ; i += 2)
    {
        sum += f/i;
        if (1.0/i <= 1e-14) break;
        f = -f;
    }
    cout<<"The PI is "<<setprecision(15)<<4*sum<<endl;
}
