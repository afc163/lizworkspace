#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "inten.h"
#include "dis.h"
#include "ant.h"

//常量定义
const double alpha=1;
const double beta=2;
const double tau0=1;
const double Q=100;
const double rho=0.5;

//初始化
void init_inten(dis_inten **d)
{
	int i,j;
	for (i=0;i<n;i++)
		for (j=0;j<n;j++)
		{
			d[i][j].inten=tau0;			
		}	  
}

//数据初始化
void reset_delta_tao(dis_inten **d)
{
	int i,j;
	for (i=0;i<n;i++)
		for (j=0;j<n;j++)
			d[i][j].delta_inten=0.0;
}






