/*
  @author:Sonia
  @time:
  @instruction:
*/

#ifndef dis_HEAD
#define dis_HEAD
/*城市的坐标结构体*/
struct locate
{
	int nod;
	int x;
	int y;
}; 							
/*距离结构体，存放城市距离及dij信息素*/       
struct dis_inten
{
    double dis;
    double inten;
    double delta_inten;
};

typedef dis_inten dis_inten;

extern dis_inten **d;
extern int n;	
double ijdist(double x,double y);   /*函数：计算城市之间距离*/
dis_inten** CreateMatrix(int nRow, int nCol);
void DeleteMatrix(dis_inten ***pppT, int nRow);

#endif

