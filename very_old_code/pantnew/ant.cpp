#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <malloc.h> 
#include <process.h> 
#include <time.h>
#include "dis.h"
#include "ant.h" 

//��������
const double alpha=1;
const double beta=3;
const double tau0=1;
const double Q=100;
const double rho=0.5;
const int ANT_NUM=20;


//��ʼ��
void init_ant(ants *pant)
{
	pant->num=0;
	pant->tabu=(int *)malloc(sizeof(int)*(n+1));
	pant->allow=(int *)malloc(sizeof(int)*(n+1));
	pant->length=0.0;
	memset(pant->tabu,0,sizeof(int)*(n+1));
	memset(pant->allow,0,sizeof(int)*(n+1));
	
}

//�ͷ��ڴ���Դ
void free_ant(ants *pant)
{
	free(pant->tabu);
	free(pant->allow);
}

//ѡ����һ����
int choose_next_city(ants *pant)
{
	int i,j,t,k;
	double *temp=NULL,*prob=NULL;
	double sum=0.0,eta=1.0,tau=1.0;
	i=pant->num;
//	pant->allow[i]=1;
	//��̬�ڴ����
	temp=(double*)malloc(n*sizeof(double));
  prob=(double*)malloc(n*sizeof(double));

	for (j=0;j<n;j++)
	{
		if (pant->allow[j]==0)
		{
		    for (k=1;k<=beta;k++)
		    		eta=eta/d[i][j].dis;
		    for (k=1;k<=alpha;k++)
		        tau=tau*d[i][j].inten;
		   /* printf("%f",eta);
		    scanf("%d",&k);*/ 
		    temp[j]=tau*eta;
		    eta=1.0;
		    tau=1.0;
		}
		else temp[j]=0.0;
		
		sum=sum+temp[j];
	}

	for (j=0;j<n;j++)
	{
		prob[j]=temp[j]/sum;
	}
	for (j=0;j<n-1;j++)
	{
		prob[j+1]=prob[j]+prob[j+1];
	}
	for(j=0;j<n;j++)
	{
		prob[j]=prob[j]*10;
	}	
	t=rand()%10;
	for (j=0;j<n;j++)
	{
		if (prob[j]>t) 
		{
			pant->allow[j]=1;
			return j;
		}
	}
	return 0;
}

//�������
double caculeate_distence(ants pant)
{
	int i;
	double len=0.0;
	for (i=1;i<n;i++)
	{	
		len=len+d[pant.tabu[i]][pant.tabu[i+1]].dis;
	}
	len=len+d[pant.tabu[n]][pant.tabu[1]].dis;
	return len;
}
//��ӡ·��
void print_ant_tour(int *a)
{
	int i;
	for (i=1;i<=n;i++)
		printf("%d->",a[i]);
	printf("%d\n",a[1]);
}
//������
void clear_tabu(ants *pant)
{
	int i,j;
	for (i=1;i<=ANT_NUM;i++)
		for(j=1;j<=n;j++)
			pant[i].tabu[j]=0;
}
