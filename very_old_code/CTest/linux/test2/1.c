//进程的异步并发
/*要求：该程序运行时，系统中有一个父进程和两个子进程在活动，每个进程在屏幕上显示不同信息以表明正在CPU上执行。*/

#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

main()
{
  int pid1,pid2;
  pid1=fork();
  if (pid1<0)
      {
        printf("fork pid1 error\n");
        exit(0);
      }
   else if (pid1 == 0)
        printf("this is child pid1 and PID is %d and parent PID is %d\n",getpid(),getppid());
     else { 
         pid2=fork();
         if (pid2<0)
              {
                 printf("fork pid2 error\n");
                 exit(0);
              }
           else if (pid2 == 0)
       printf("this is child pid2 and PID is %d and parent PID is %d\n",getpid(),getppid());
    else     printf("this is parent pid and PID is %d\n",getpid());
}
    exit(0);
}
