//时钟程序，实现每一秒显示一次当前时间

#include<unistd.h>
#include<signal.h>
#include<time.h>

int k=10;
char *wday[]={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
time_t timep;
struct tm *p;

static void sig_alarm()
{
    time(&timep);
    p=gmtime(&timep);
    printf("%d %d %d ",(1900+p->tm_year), (1+p->tm_mon),p->tm_mday);
    printf("%s %d:%d:%d\n", wday[p->tm_wday], p->tm_hour, p->tm_min, p->tm_sec); 
}

main()
{ 
    if (signal(SIGALRM,sig_alarm)==SIG_ERR) printf("error!");
    while(k!=0) { 
       k--;
       alarm(1);       //发出SIGALRM
       pause();   
    }   
    alarm(0);      
}
