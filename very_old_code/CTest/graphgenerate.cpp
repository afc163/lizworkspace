#include "stdio.h"
#include "stdlib.h"
#include "math.h"
#include "string.h"
#define graph_scale 17          //ÿ��ͼ��ģgraph scale (����*����)
#define graph_num 150           //ͼ����Ŀgraph number

int g[graph_scale][graph_scale];
int freq[9126][5];              //��Ÿ��ߵ�Ƶ����,��ģ13*27*26
int side=0,dot=0;
char sname[15],dname[15];

int randn()
{
	int i;
	do
	{
		i=rand();
	}while(i<0);
    return i;
}

int jcalculate(int j)
{
	int i,sum,n;
	sum=0;
	n=26;
    for(i=0;i<j;i++)
	{
		sum=sum+n;
		n--;
	}
	return sum;
}

void initial1()
{
	int i,j,k,t;
	for(i=0;i<9126;i++)
		for(j=0;j<5;j++)
			freq[i][j]=0;
    for(i=0;i<26;i++)
		for(j=0;j<26;j++)
			for(k=j;k<26;k++)
			{
				t=jcalculate(j);
				freq[(i*351)+t+(k-j)][0]=i+65;
				freq[(i*351)+t+(k-j)][1]=j+97;
				freq[(i*351)+t+(k-j)][2]=k+97;
			}
}

void initial2()      //��ͼ�Ĺ��������ʼ��Ϊ0
{
	int i,j;
	for(i=0;i<graph_scale;i++)
		for(j=0;j<graph_scale;j++)
			g[i][j]=0;
}

void initial3()
{
	int i;
	for(i=0;i<9126;i++)
		freq[i][4]=0;
}
	
void generate1()      //����ͼ�Ĺ��������б���б�
{
	int i,j,r,p,flag;
	char a[27],b[27];
	for(i=1;i<27;i++)
	{
		a[i]=64+i;
		b[i]=96+i;
	}

    //�����ʼ���߱�
	i=2;
	flag=1;         //flag��ס�õڼ�����ĸ
	while(i<graph_scale)
	{
		r=randn()%8;
		j=1;
        for(p=i;p<i+r&&p<graph_scale;p++)
		{
			g[0][p]=a[flag];
			g[1][p]=j;
			j++;
		}
        flag++;
		i=i+r;
	}
	if(side<flag)
			side=flag;

    //�����ʼ�����
    i=2;
	flag=1;         //flag��ס�õڼ�����ĸ
	while(i<graph_scale)
	{
		r=randn()%6;
		j=1;
        for(p=i;p<i+r&&p<graph_scale;p++)
		{
			g[p][0]=b[flag];
			g[p][1]=j;
			j++;
		}
        flag++;
		i=i+r;
	}
}

int testback(int i, int r1, int r2)
{
	int s,flag=0;
	for(s=2;s<i;s++)
	{
		if(g[r1][s]==1&&g[r2][s]==1)
		{
			flag=1;
			break;
		}
	}
    return flag;
}

void generate2()  //����ע�����������
{
	int i,r1,r2,r,flag;
	for(i=2;i<graph_scale;i++)
	{
		do
		{
			r1=randn()%(graph_scale-2)+2;
		    r2=randn()%(graph_scale-2)+2;
			flag=testback(i,r1,r2);
		}while(r1==r2||flag==1);
        if(r1>r2)             //��r1<r2
		{
			r=r1;
			r1=r2;
			r2=r;
		}
		if(freq[((g[0][i]-65)*351)+jcalculate(g[r1][0]-97)+(g[r2][0]-g[r1][0])][4]!=1)
		{
			freq[((g[0][i]-65)*351)+jcalculate(g[r1][0]-97)+(g[r2][0]-g[r1][0])][3]++;
			/*if(freq[((g[0][i]-65)*351)+jcalculate(g[r1][0]-97)+(g[r2][0]-g[r1][0])][3]>9)
			{
				printf("Yes\n");
			}*/
            freq[((g[0][i]-65)*351)+jcalculate(g[r1][0]-97)+(g[r2][0]-g[r1][0])][4]=1;
		}
        g[r1][i]=1;
		g[r2][i]=1;
	}
	
}

void txt_out1(char filename[10])  //�����ɵ�ͼд���ı�
{
	int i,j,k;
	FILE* fp;
	fp = fopen(filename,"w");
	if(fp!=NULL)
	{
		printf("%s has been created!\n", filename);
	}
	else
	{
		printf("Cannot creat %s\n",filename);
	}
	for(i=0;i<graph_scale;i++)
	{
		for(j=0;j<graph_scale;j++)
		{
			k=g[i][j];
			if(k>64)
			{
				fprintf(fp,"%c",k);
			}
			else
				fprintf(fp,"%d",k);
		}
		fputc('\n',fp);
	}
	fclose(fp);
}

void txt_out2(char filename[15])
{
	int i,j,k;
	FILE* fp;
	fp = fopen(filename,"w");
	if(fp!=NULL)
	{
		printf("%s has been created!\n", filename);
	}
	else
	{
		printf("Cannot creat %s\n",filename);
	}
    for(i=0;i<9126;i++)
	{
		for(j=0;j<4;j++)
		{
			k=freq[i][j];
			if(k>64)
			{
				fprintf(fp,"%c",k);
			}
			else
				fprintf(fp,"%d",k);
		}
		fputc('\n',fp);
	}
	fclose(fp);
}

int main()
{
	int i,j,k,gn=1;
//	int g[graph_scale][graph_scale];
	char filename2[5]={'.','t','x','t','\0'};
	char filename3[15]="frequency.txt";
	initial1();               //��ʼ��¼Ƶ���ȵľ���
//	char filename1[10];
	while(gn<=graph_num)
	{
		i=48+int(gn/100);
		j=48+(gn-int(gn/100)*100)/10;
		k=48+gn-int(gn/100)*100-(j-48)*10;
		char filename1[15]={i,j,k,'\0'};
        strcat(filename1,filename2);    //���±��ļ���
		initial3();
		initial2();       //��ͼ�Ĺ��������ʼ��Ϊ0
		generate1();     //����ͼ�Ĺ��������б���б�
        generate2();     //����ע�����������
		txt_out1(filename1);   //��ͼ�ŵ����±���
		if(g[0][31]>side)
		{
			side=g[0][31];
			strcpy(sname,filename1);
		}
		if(g[31][0]>dot)
		{
			dot=g[31][0];
			strcpy(dname,filename1);
		}
		gn++;
	}
	txt_out2(filename3);
	printf("Max sidesign is %c in %s.\n",side,sname);
	printf("Max potsign is %c in %s.\n",dot,dname);

}
