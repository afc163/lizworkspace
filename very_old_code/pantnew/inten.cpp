#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "inten.h"
#include "dis.h"
#include "ant.h"

//��������
const double alpha=1;
const double beta=2;
const double tau0=1;
const double Q=100;
const double rho=0.5;

//��ʼ��
void init_inten(dis_inten **d)
{
	int i,j;
	for (i=0;i<n;i++)
		for (j=0;j<n;j++)
		{
			d[i][j].inten=tau0;			
		}	  
}

//���ݳ�ʼ��
void reset_delta_tao(dis_inten **d)
{
	int i,j;
	for (i=0;i<n;i++)
		for (j=0;j<n;j++)
			d[i][j].delta_inten=0.0;
}






