//实现进程间的软中断通信。
/*要求：父子进程通过信号完成终止过程，具体为，父进程创建两个子进程后，通过捕捉键盘发来的Delete中断信号而向两个子进程发出杀死信号，子进程捕捉信号后分别输出下列信息后终止：
Child Process1 is Killed by Parent!
Child Process2 is Killed by Parent!
父进程在子进程终止后，输出下列信息后自我终止：
Parent Process is Killed! */

#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>

int wait_flag=1;
void stop();

main()
 { int pid1,pid2;
   signal(SIGINT,stop);  // 按Ctl+C键发出中断信号，按Delete键好象不行
   while ((pid1=fork()) ==-1) ;
   if (pid1>0)
     { while ((pid2=fork())==-1) ;
       if (pid2>0)
         {
          while(wait_flag==1) ; //等待中断信号
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
           while (wait_flag==1) ;
           printf("\n Child process 2 is killed by parent !!\n");
           exit(0);
          }
      }
       else
          { wait_flag=1;
            signal(16,stop);
            while (wait_flag==1) ;
            printf("\n Child process 1 is killed by parent !!\n");
            exit(0);
          }
     }
  void stop()
     { 
       wait_flag=0; 
     }   
