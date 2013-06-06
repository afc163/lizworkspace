#include "stdio.h"
#include "stdlib.h"
#include "math.h"
#include "string.h"
#define graph_scale 17          //每个图规模graph scale (行数*列数)
#define graph_num 150           //图的数目graph number

int g[graph_scale][graph_scale];
int freq[9126][5];              //存放各边的频繁度,规模13*27*26
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

void initial2()      //将图的关联矩阵初始化为0
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
	
void generate1()      //生成图的关联矩阵行标和列标
{
	int i,j,r,p,flag;
	char a[27],b[27];
	for(i=1;i<27;i++)
	{
		a[i]=64+i;
		b[i]=96+i;
	}

    //下面初始化边标
	i=2;
	flag=1;         //flag记住用第几个字母
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

    //下面初始化点标
    i=2;
	flag=1;         //flag记住用第几个字母
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

void generate2()  //将边注入关联矩阵中
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
        if(r1>r2)             //让r1<r2
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

void txt_out1(char filename[10])  //将生成的图写入文本
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
	initial1();               //初始记录频繁度的矩阵
//	char filename1[10];
	while(gn<=graph_num)
	{
		i=48+int(gn/100);
		j=48+(gn-int(gn/100)*100)/10;
		k=48+gn-int(gn/100)*100-(j-48)*10;
		char filename1[15]={i,j,k,'\0'};
        strcat(filename1,filename2);    //记事本文件名
		initial3();
		initial2();       //将图的关联矩阵初始化为0
		generate1();     //生成图的关联矩阵行标和列标
        generate2();     //将边注入关联矩阵中
		txt_out1(filename1);   //将图放到记事本中
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
