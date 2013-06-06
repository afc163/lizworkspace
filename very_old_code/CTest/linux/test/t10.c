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
           {
             int k1=5;
             while( k1>0 ) { 
                           k1--; 
                           sleep(1); 
                           printf("this is child pid1 and PID is %d and parent PID is %d\n",getpid(),getppid());
                         }
           }
   else { 
         pid2=fork();
         if (pid2<0)
              {
                 printf("fork pid2 error\n");
                 exit(0);
              }
           else if (pid2 == 0)
              {
                 int k2=5;
                 while( k2>0 ) { 
                           k2--; 
                           sleep(1); 
                           printf("this is child pid2 and PID is %d and parent PID is %d\n",getpid(),getppid());
                         }
               }
               else {
                 int k3=5;
                 while( k3>0 ) { 
                           k3--; 
                           sleep(1); 
                           printf("this is parent pid and PID is %d\n",getpid());
                         }
               }
       }
    exit(0);
}
