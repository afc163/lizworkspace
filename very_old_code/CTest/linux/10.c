//进程的异步并发


#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

main()
{
  int pid1,pid2,i;
  while((pid1=fork())==-1) ;
   if (pid1>0)
     { while ((pid2=fork())==-1) ;
       if (pid2>0)
         {
          for(i=1;i<50;i++) printf("Parent process !!\n");
         }
       else
         { 
          for(i=1;i<50;i++) printf("Child process2 !!\n");
          }
     }
   else
     { 
        for(i=1;i<50;i++) printf("Child process1 !!\n");
     }
        exit(0);
}
