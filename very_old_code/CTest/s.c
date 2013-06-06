#include <stdio.h>

struct student
{
  char name[20];
  int  age;
};
void printst1(struct student * *pst)
{
	 struct student * one,*two;
    one = pst[0];
    two = pst[1];
	 printf("%s %d\n", one->name, one->age);
	 printf("%s %d\n", two->name, two->age);
}

void printst(int pst)
{
	 struct student one,two;
    one = *(*((struct student **)pst));
    two = *(*((struct student **)(pst+4)));
	 printf("%s %d\n", one.name, one.age);
	 printf("%s %d\n", two.name, two.age);
}
int main()
{
  struct student *pst[2];
  struct student t1,t2;
  pst[0] = &t1;
  pst[1] = &t2;
  strcpy(t1.name,"liu");
  t1.age=20;
  strcpy(t2.name,"sh");
  t2.age=30;
  printst((int)pst);
  return 0; 
}

