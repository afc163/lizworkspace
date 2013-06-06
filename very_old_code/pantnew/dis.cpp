#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <malloc.h> 
#include <process.h> 
#include "dis.h"

//计算两点之间距离
double ijdist(double x,double y)     /*函数：输入城市坐标xi-xj，yi-yj计算距离*/
{
  return sqrt(x*x+y*y);
}
//动态分配二维矩阵
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
//删除二维矩阵，释放内存
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
