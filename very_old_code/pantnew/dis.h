/*
  @author:Sonia
  @time:
  @instruction:
*/

#ifndef dis_HEAD
#define dis_HEAD
/*���е�����ṹ��*/
struct locate
{
	int nod;
	int x;
	int y;
}; 							
/*����ṹ�壬��ų��о��뼰dij��Ϣ��*/       
struct dis_inten
{
    double dis;
    double inten;
    double delta_inten;
};

typedef dis_inten dis_inten;

extern dis_inten **d;
extern int n;	
double ijdist(double x,double y);   /*�������������֮�����*/
dis_inten** CreateMatrix(int nRow, int nCol);
void DeleteMatrix(dis_inten ***pppT, int nRow);

#endif

