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

  int pid1,pid2,i,j;
  char * str1;
  char * str2;

  pid1=fork();
  if (pid1 == 0)
           {  printf("child1\n");
             scanf("%s",str1);
             write(filedes[1],str1,sizeof(str1));
             exit(1);
           }
   pid2=fork();
   if (pid2 == 0)
           {   printf("child2\n");
              scanf("%s",str2);
              write(filedes[1],str2,sizeof(str2));
              exit(2);
           }
   if (waitpid(pid1,NULL,0)==pid1) {                   //变大写
                            read(filedes[0],buffer,80);
                            for(i=0;i<sizeof(str1);i++) buffer[i]=toupper(buffer[i]);
                            printf("parent--child1: %s\n",buffer);
                          }
                       else  printf("waitpid1 error!"); 
   if (waitpid(pid2,NULL,0)==pid2) {                  //变小写 
                            read(filedes[0],buffer,80);
                            for(j=0;j<sizeof(str2);j++) buffer[j]=tolower(buffer[j]);
                            printf("parent--child2:%s\n",buffer);
                          }
                       else printf("waitpid2 error!");
   exit(0);
}
