#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

main()
{
  int pid1,pid2;
  
  if ( (pid1=fork())<0 || (pid2=fork())<0 )
      {
        printf("fork pid12 error\n");
        exit(0);
      }
   else if (pid1 == 0)
             printf("this is child pid1 and PID is %d and parent PID is %d\n",getpid(),getppid());
   else if (pid2 == 0) 
             printf("this is child pid2 and PID is %d and parent PID is %d\n",getpid(),getppid());
   else
             printf("this is parent pid and PID is %d\n",getpid());
   exit(0);
}
