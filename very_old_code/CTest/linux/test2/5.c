//采用请求页式存储管理技术，通过FIFO,LRU置换算法模拟设计

#include <stdio.h>

#define TRUE 1
#define FALSE 0
#define INVALID -1

#define total_instruction 320
#define total_vp 32

typedef struct{
  int pn,pfn,counter,time;
}pl_type;
pl_type pl[total_vp];

struct pfc_struct{
   int pn,pfn;
   struct pfc_struct * next;
};

typedef  struct pfc_struct pfc_type;
pfc_type  pfc[total_vp], * freepf_head, * busypf_head, * busypf_tail;

int diseffect,a[total_instruction];
int page[total_instruction],offset[total_instruction];

void initialize(int total_pf);
void FIFO(int total_pf);
void LRU(int total_pf);

main()
{
  int S,i,j;   
  srand(getpid()*10);              
  S=(float)319*rand()/32767+1;  
  for (i=0;i<total_instruction;i+=4)
   {
     a[i]=S;
     a[i+1]=a[i]+1;
     a[i+2]=(float)a[i]*rand()/32767;
     a[i+3]=a[i+2]+1;
     S=(float)rand()*(318-a[i+2])/32767+a[i+2]+2;
   }
  for (i=0;i<total_instruction;i++)
   {
     page[i]=a[i]/10;
     offset[i]=a[i]%10;
   }   
  for (i=4;i<=32;i++)
   {  
     printf("%2d page frames",i);   
     FIFO(i); 
     LRU(i);
     printf("\n");
   }
}

void FIFO(int total_pf)
 { 
   int i,j;
   pfc_type * p,* t;         
   initialize(total_pf);
   busypf_head=busypf_tail=NULL;
   for(i=0;i<total_instruction;i++)
      if(pl[page[i]].pfn == INVALID)
        {  
          diseffect+=1;
          if(freepf_head==NULL)
          {
            p=busypf_head->next;
            pl[busypf_head->pn].pfn=INVALID;
            freepf_head=busypf_head;
            freepf_head->next=NULL;
            busypf_head=p;
           }
          p=freepf_head->next;
          freepf_head->next=NULL;
          freepf_head->pn=page[i];
          pl[page[i]].pfn=freepf_head->pfn;
          if(busypf_tail==NULL)
               busypf_head=busypf_tail=freepf_head;
           else
            {
              busypf_tail->next=freepf_head;
              busypf_tail=freepf_head;
            }
          freepf_head=p;
        }
    printf("FIFO:%6.4f",1-(float)diseffect/320);
}

void LRU(int total_pf)
{
  int min,minj,i,j,present_time;
  initialize(total_pf);
  present_time=0;
  for(i=0;i<total_instruction;i++)
    {
      if(pl[page[i]].pfn=INVALID)
       {
         diseffect++;
         if(freepf_head=NULL)
           {
             min=32767;
             for(j=0;j<total_vp;j++)
               if(min>pl[j].time&&pl[j].pfn!=INVALID)
                   {
                      min=pl[j].time;
                      minj=j;
                   }
             freepf_head=&pfc[pl[minj].pfn];
             pl[minj].pfn=INVALID;
             pl[min].time=-1;
             freepf_head->next=NULL;
           }
         pl[page[i]].pfn=freepf_head->pfn;
         pl[page[i]].time=present_time;
         freepf_head=freepf_head->next;
       }
      else
        pl[page[i]].time=present_time;
      present_time++;
    }
  printf("LRU:%6.4f",1-(float)diseffect/320);
}

void initialize(int total_pf)
{
  int i;
  diseffect=0;
  for(i=0;i<total_vp;i++)
    {
      pl[i].pn=i;
      pl[i].pfn=INVALID;
      pl[i].counter=0;
      pl[i].time=-1;
    }
  for(i=1;i<total_pf;i++)
    {
      pfc[i-1].next=&pfc[i];
      pfc[i-1].pfn=i-1;
    }
  pfc[total_pf-1].next=NULL;
  pfc[total_pf-1].pfn=total_pf-1;
  freepf_head=&pfc[0];

}
