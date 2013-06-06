#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <malloc.h> 
#include <process.h> 
#include "dis.h"

//��������֮�����
double ijdist(double x,double y)     /*�����������������xi-xj��yi-yj�������*/
{
  return sqrt(x*x+y*y);
}
//��̬�����ά����
dis_inten** CreateMatrix(int nRow, int nCol)
{
	//dis_inten **ppT = new T*[nRow];
	dis_inten **ppT = (dis_inten **)malloc(sizeof(dis_inten *)*nRow);
    for(int iRow = 0;iRow<nRow;iRow++)
	{
    //ppT[iRow] = new T[nCol];
    ppT[iRow] = (dis_inten *)malloc(sizeof(dis_inten)*nCol);
	}
  return ppT;
}
//ɾ����ά�����ͷ��ڴ�
void DeleteMatrix(dis_inten ***pppT, int nRow)
{
	for(int iRow=0; iRow<nRow; iRow++){
    //delete[] (*pppT)[iRow];
    free((*pppT)[iRow]);
  }
  free(*pppT);
  //delete[] (*pppT);
  (*pppT) = NULL;
}
