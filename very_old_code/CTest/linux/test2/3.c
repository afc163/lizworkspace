//实现进程间管道通信
/*要求：父子进程共享一无名管道，两个子进程作为发送方分别向管道发送一行信息，
父进程先读出子进程P1发来的信息行，将其转换为大写字母输出；再读出子进程P2发
来的信息行，将其转换为小写字母输出。*/

#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <ctype.h>

int filedes[2];
char buffer[80];

main()
{
  pipe(filedes);

  char  str1[80];
  char  str2[80];
  int pid1,pid2,i;

  while((pid1=fork())==-1) ;
  if (pid1 == 0)
           { 
             lockf(filedes[1],1,0);
             printf("child1 input string1\n");
             scanf("%s",str1);
             write(filedes[1],str1,sizeof(str1));
             lockf(filedes[1],0,0);
             exit(0);
           }
  else { 
         while((pid2=fork())==-1) ;
         if (pid2 == 0)
           {  
              lockf(filedes[1],1,0);
              printf("child2 input string2\n");
              scanf("%s",str2);
              write(filedes[1],str2,sizeof(str2));
              lockf(filedes[1],0,0);
              exit(0);
           }
         else     
         { if (waitpid(pid1,NULL,0)==pid1) {                   //变大写
                            read(filedes[0],buffer,80);
                            for(i=0;i<sizeof(str1);i++) buffer[i]=toupper(buffer[i]);
                            printf("parent--child1: %s\n",buffer);
                          }
                       else  printf("waitpid1 error!"); 
          if (waitpid(pid2,NULL,0)==pid2) {                  //变小写
                            read(filedes[0],buffer,80);
                            for(i=0;i<sizeof(str2);i++) buffer[i]=tolower(buffer[i]);
                            printf("parent--child2: %s\n",buffer);
                          }
                       else printf("waitpid2 error!");
          exit(0);
         }
     }
}
