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

typedef long clock_t;//计时器，显示程序运行时间
int n = 0;
dis_inten **d;

//常量定义
const int NMAX=100;
const double rho=0.98;
const double Q=100;
const int ANT_NUM=50;

int main(void)
{
	FILE *stream;
	int i,j;

    clock_t start,finish;//记录程序开始执行时间和结束时间
    double totaltime;
   
    int x,y,k,m,minnum0,minnum;
    char a[50]; 
    int s=1;
    double s0=0.0,min0,min;
    int NC=1;
	
	  //结构体定义
   struct locate city_loc[100]; 
    struct ants ant[ANT_NUM];
  
    //初始化
    d=NULL;
  
    start=clock();//记录起始时间
  
    //打开tsp文件
    if ((stream=fopen("eil51.tsp","r"))==NULL)
	  	{
				printf("Cannot open output file.\n");
				return 1;
	  	}								
 
    //取城市个数N，将光标移动到第一个城市坐标处
	  for (i=1;i<=6;i++)
	 		{
	    	if (i==4)  fscanf(stream,"DIMENSION : %d\n",&n);
	    	else fgets(a,100,stream);
	  	}
	 
     //动态创建矩阵
    d = CreateMatrix(n, n);
	
	  int *tabu_min=NULL;
	  tabu_min=(int*)malloc((n+1)*sizeof(int));
    if(tabu_min==NULL)//内存分配失败
	  	{
	    	printf("malloc failed!please chick!");
	      exit(1);
	 		}
	  
	  //动态创建城市坐标结构体
	/*  locate *city_loc=NULL;
	  city_loc=(locate*)malloc((n+1)*sizeof(locate));
	  if(city_loc==NULL)
	  	{
	  		printf("malloc failed!please chick!");
	  		exit(1);
	  	}*/
	  
	  //将各城市坐标存入结构数组city_loc中
    for (i=0;i<n;i++)
			fscanf(stream,"%d %d %d\n",&city_loc[i].nod,&city_loc[i].x,&city_loc[i].y);
	  
    for (i=0;i<n;i++)	
    	for (j=0;j<n;j++)
      	{
         		x=abs(city_loc[i].x-city_loc[j].x);   
            y=abs(city_loc[i].y-city_loc[j].y);
            d[i][j].dis=ijdist(x,y);   
        }
    //调用函数ijdist将城市ij的距离存入数组d中
     
  /*  for (i=0;i<n;i++)
     {
         for (j=0;j<n;j++)
    	   printf("%f ",d[i][j].dis);
         printf("\n");
     }*/
 
     //初始化蚂蚁30只蚂蚁
     for (i=1;i<=ANT_NUM;i++)
     {
         init_ant(&ant[i]);
     }         
     
     //将30只蚂蚁随机放置于第m个城市上
     for (k=1;k<=ANT_NUM;k++)
	   		{
		     		m=rand()%n;
		    		ant[k].num=m;
	 			}        
	 
     init_inten(d);  /*初始化城市之间的信息素*/
              
     reset_delta_tao(d); /*重置tao*/
              
     for (k=1;k<=ANT_NUM;k++)
	       ant[k].tabu[s]=ant[k].num;    /*初始化禁忌表*/
	            
     /*初始化结束*/
     
     min0=1.7e+308;
	   min=1.7e+308;    /*min0存放30蚂蚁遍历一次后的最优解，min存放NMAX循环一次的最优解*/
     
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
			            
							}/*当30只蚂蚁全部走完所得到的最优解放置于min0，蚂蚁号放置在minnum中*/
					
			 	   	
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
	    					/*取出第minnum0号蚂蚁的禁忌表，修改路径上面的信息素，到此一次蚂蚁遍历完成*/
    		
    		if (min0<min) 
        		{
									min=min0;
									minnum=minnum0;
						}
								/*比较最优解，将目前最优解存放至min，蚂蚁号存放置minnum*/
	    	for (k=1;k<n;k++) // 为什么再次更新？ 假设
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
	    				/*再次更新路径上的信息素，并将最有蚂蚁上的紧急表取出至tabu_min中*/
	    	
	    	clear_tabu(ant);
	    				/*清空蚂蚁的紧急表*/
	    	
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
		 				/*重新初始化蚂蚁*/
		 			
		 	
	 }
   
   printf("the dis is: %lf \n",min);
   print_ant_tour(tabu_min);
     /*打印路径*/
   
   DeleteMatrix(&d, n);
   finish=clock();//记录结束时间
   totaltime=(double)(finish-start)/CLOCKS_PER_SEC;
   printf("this program has take %f s",totaltime);
	 return 0;
}







