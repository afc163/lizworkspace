/*
  @author:Soina
  @time:
  @instruction:
*/
#ifndef __ANT_H__
#define __ANT_H__
/* ���Ͻṹ�壬��Ž��ɱ�����ѡ��ĳ���, �������еľ��� */      	                          
struct ants
{
	int num;
    int  *tabu;
	int  *allow;
	double length;
};            

extern int n;
extern dis_inten **d;

void   init_ant(ants *pant);
void   free_ant(ants *pant);
int choose_next_city(ants *pant);  
double caculeate_distence(ants pant);
void print_ant_tour(int *a);
void clear_tabu(ants *pant);

#endif	
