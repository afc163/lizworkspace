#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>

int wait_flag=1;
void stop();

main()
 { int pid1,pid2;
   signal(SIGINT,stop);   
   while ((pid1=fork()) ==-1) ;
   if (pid1>0)
     { while ((pid2=fork())==-1) ;
       if (pid2>0)
         {
          while(wait_flag==1) pause();
          kill (pid1,16);
          kill (pid2,17);
          wait(0);
          wait(0);
          printf("\n Parent process is killed !!\n");
          exit(0);
         }
       else
         { wait_flag=1;
           signal(17,stop);
           while (wait_flag==1) pause();
           printf("\n Child process 2 is killed by parent !!\n");
           exit(0);
          }
      }
       else
          { wait_flag=1;
            signal(16,stop);
            while (wait_flag==1) pause();
            printf("\n Child process 1 is killed by parent !!\n");
            exit(0);
          }
     }
  void stop()
     { 
       wait_flag=0; 
     }   
