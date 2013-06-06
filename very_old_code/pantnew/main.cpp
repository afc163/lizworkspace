/*
  @author:Soina
  @time:
  @instruction:
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <malloc.h> 
#include <process.h> 
#include<time.h>

#include "dis.h"
#include "ant.h"
#include "inten.h"

typedef long clock_t;//��ʱ������ʾ��������ʱ��
int n = 0;
dis_inten **d;

//��������
const int NMAX=100;
const double rho=0.98;
const double Q=100;
const int ANT_NUM=50;

int main(void)
{
	FILE *stream;
	int i,j;

    clock_t start,finish;//��¼����ʼִ��ʱ��ͽ���ʱ��
    double totaltime;
   
    int x,y,k,m,minnum0,minnum;
    char a[50]; 
    int s=1;
    double s0=0.0,min0,min;
    int NC=1;
	
	  //�ṹ�嶨��
   struct locate city_loc[100]; 
    struct ants ant[ANT_NUM];
  
    //��ʼ��
    d=NULL;
  
    start=clock();//��¼��ʼʱ��
  
    //��tsp�ļ�
    if ((stream=fopen("eil51.tsp","r"))==NULL)
	  	{
				printf("Cannot open output file.\n");
				return 1;
	  	}								
 
    //ȡ���и���N��������ƶ�����һ���������괦
	  for (i=1;i<=6;i++)
	 		{
	    	if (i==4)  fscanf(stream,"DIMENSION : %d\n",&n);
	    	else fgets(a,100,stream);
	  	}
	 
     //��̬��������
    d = CreateMatrix(n, n);
	
	  int *tabu_min=NULL;
	  tabu_min=(int*)malloc((n+1)*sizeof(int));
    if(tabu_min==NULL)//�ڴ����ʧ��
	  	{
	    	printf("malloc failed!please chick!");
	      exit(1);
	 		}
	  
	  //��̬������������ṹ��
	/*  locate *city_loc=NULL;
	  city_loc=(locate*)malloc((n+1)*sizeof(locate));
	  if(city_loc==NULL)
	  	{
	  		printf("malloc failed!please chick!");
	  		exit(1);
	  	}*/
	  
	  //���������������ṹ����city_loc��
    for (i=0;i<n;i++)
			fscanf(stream,"%d %d %d\n",&city_loc[i].nod,&city_loc[i].x,&city_loc[i].y);
	  
    for (i=0;i<n;i++)	
    	for (j=0;j<n;j++)
      	{
         		x=abs(city_loc[i].x-city_loc[j].x);   
            y=abs(city_loc[i].y-city_loc[j].y);
            d[i][j].dis=ijdist(x,y);   
        }
    //���ú���ijdist������ij�ľ����������d��
     
  /*  for (i=0;i<n;i++)
     {
         for (j=0;j<n;j++)
    	   printf("%f ",d[i][j].dis);
         printf("\n");
     }*/
 
     //��ʼ������30ֻ����
     for (i=1;i<=ANT_NUM;i++)
     {
         init_ant(&ant[i]);
     }         
     
     //��30ֻ������������ڵ�m��������
     for (k=1;k<=ANT_NUM;k++)
	   		{
		     		m=rand()%n;
		    		ant[k].num=m;
	 			}        
	 
     init_inten(d);  /*��ʼ������֮�����Ϣ��*/
              
     reset_delta_tao(d); /*����tao*/
              
     for (k=1;k<=ANT_NUM;k++)
	       ant[k].tabu[s]=ant[k].num;    /*��ʼ�����ɱ�*/
	            
     /*��ʼ������*/
     
     min0=1.7e+308;
	   min=1.7e+308;    /*min0���30���ϱ���һ�κ�����Ž⣬min���NMAXѭ��һ�ε����Ž�*/
     
     for (NC=1;NC<=NMAX;NC++)   
     {
	 	 				
	 	 			for (i=1;i<=ANT_NUM;i++)
		 					{
			 						for (k=2;k<=n;k++)
			 								{
			     									j=choose_next_city(&ant[i]);
				 										ant[i].num=j; 
				 										ant[i].tabu[k]=j;
				 										ant[i].allow[j]=1;             
			     									s0=s0+d[ant[i].tabu[k-1]][ant[i].tabu[k]].dis;		              
			 								}	    
			 						 		     
			 						if (s0<min0) 
			 								{
				 										min0=s0;
			     									minnum0=i;		              
			 								}
			 						s0=0.0;
			            
							}/*��30ֻ����ȫ���������õ������Ž������min0�����Ϻŷ�����minnum��*/
					
			 	   	
        for(k=1;k<n;k++)
						{	
									i=ant[minnum0].tabu[k];
									j=ant[minnum0].tabu[k+1];
									if (i<j)
										d[i][j].inten=rho*d[i][j].inten+Q/min0;
									else 
										d[j][i].inten=rho*d[j][i].inten+Q/min0;
						}	    
	    	i=ant[minnum0].tabu[1];
	    	j=ant[minnum0].tabu[n];
	    	if (i<j)
						d[i][j].inten=rho*d[i][j].inten+Q/min0;
				else 
						d[j][i].inten=rho*d[j][i].inten+Q/min0;
	    					/*ȡ����minnum0�����ϵĽ��ɱ��޸�·���������Ϣ�أ�����һ�����ϱ������*/
    		
    		if (min0<min) 
        		{
									min=min0;
									minnum=minnum0;
						}
								/*�Ƚ����Ž⣬��Ŀǰ���Ž�����min�����ϺŴ����minnum*/
	    	for (k=1;k<n;k++) // Ϊʲô�ٴθ��£� ����
						{	
		    					i=ant[minnum].tabu[k];
		    					j=ant[minnum].tabu[k+1];
									if (i<j)
										d[i][j].inten=rho*d[i][j].inten+Q/min0;
									else 
										d[j][i].inten=rho*d[j][i].inten+Q/min0;
									for (i=1;i<=n;i++)
											tabu_min[i]=ant[minnum].tabu[i];
						}	     
	    	i=ant[minnum].tabu[1];
	    	j=ant[minnum].tabu[n];
	    	if (i<j)
						d[i][j].inten=rho*d[i][j].inten+Q/min0;
				else 
						d[j][i].inten=rho*d[j][i].inten+Q/min0;
	    				/*�ٴθ���·���ϵ���Ϣ�أ��������������ϵĽ�����ȡ����tabu_min��*/
	    	
	    	clear_tabu(ant);
	    				/*������ϵĽ�����*/
	    	
	    	for (i=1;i<=ANT_NUM;i++)
						{
            			init_ant(&ant[i]);
						}
        
        
        for (k=1;k<=ANT_NUM;k++)
	    			{
		    					m=rand()%n;
		    					ant[k].num=m;
	    			}   
	    	
	    	for (k=1;k<=ANT_NUM;k++)
		    ant[k].tabu[s]=ant[k].num;
		 				/*���³�ʼ������*/
		 			
		 	
	 }
   
   printf("the dis is: %lf \n",min);
   print_ant_tour(tabu_min);
     /*��ӡ·��*/
   
   DeleteMatrix(&d, n);
   finish=clock();//��¼����ʱ��
   totaltime=(double)(finish-start)/CLOCKS_PER_SEC;
   printf("this program has take %f s",totaltime);
	 return 0;
}







