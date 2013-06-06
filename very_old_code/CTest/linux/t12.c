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
  if (pid1 == 0)
      { 
        printf("this is child pid1 and PID is %d and parent PID is %d\n",getpid(),getppid());
        _exit(0);
      }

  pid2=fork();
  if (pid2<0)
     {
        printf("fork pid2 error\n");
        exit(0);
     }
  if (pid2 == 0)
      {
            printf("this is child pid2 and PID is %d and parent PID is %d\n",getpid(),getppid());
            _exit(0);
      }
  printf("this is parent pid and PID is %d\n",getpid());
  exit(0);
}
