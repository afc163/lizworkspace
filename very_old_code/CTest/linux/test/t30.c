#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int filedes[2];
char buffer[80];

main()
{
  pipe(filedes);
  char * str1;
  char * str2;
  int pid1,pid2;
  pid1=fork();
  if (pid1<0)
      {
        printf("fork pid1 error\n");
        exit(0);
      }
   else if (pid1 == 0)
           { printf("child1\n");
             scanf("%s",str1);
             write(filedes[1],str1,sizeof(str1));
             exit(0);
           }
   else { 
         pid2=fork();
         if (pid2<0)
              {
                 printf("fork pid2 error\n");
                 exit(0);
              }
           else if (pid2 == 0)
              {  printf("child2\n");
                 scanf("%s",str1);
                 write(filedes[1],str2,sizeof(str2));
                 exit(0);
               }
               else {
                     if (waitpid(pid1,NULL,0)==pid1) {                   
                            read(filedes[0],buffer,80);
                            printf("parent--child1: %s\n",buffer);
                          }
                       else  printf("waitpid1 error!"); 
                     if (waitpid(pid2,NULL,0)==pid2) {                   
                            read(filedes[0],buffer,80);
                            printf("parent--child2: %s\n",buffer);
                          }
                        else printf("waitpid2 error!");
                  }
       }
    exit(0);
}
