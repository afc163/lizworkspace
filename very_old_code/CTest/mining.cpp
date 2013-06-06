#include "stdio.h"
#include "stdlib.h"
#include "string"
#include "iostream"
#include "time.h"

using namespace std;

/*************************************************************************************/
#define sup 4                  //支持度sup

#define size 17                //图的原始规模
#define graph_num 150          //图的个数
#define temp_graph_num 3500		//频繁候选图的个数
//int size_side;         //图的边数，中间会由于剪枝而变少
//int size_dot;          //图的点数，中间会由于剪枝而变少


int freq[9126][4];         //将边频繁度从txt中读出放入freq矩阵中
//int gl[size][size];        //读取图的f-1频繁项
//int gg[size][size];        //f-1添加一条边后变成f的图的矩阵
//int gf[size][size];        //生成一项集时用的矩阵
int f;						//频繁f项集
int seq_f1;					//图gn的f1项集的序号
int seq_f;					//图gn的f项集的序号
int s_f;					//频繁度f时的图的序号

int seq_tf;                 //记录生成temp候选集时标记个数

//int seq3;                  //记录除去非频繁集后的项的序号

char gn_sides[50][2*size];
	//图gn中，由f-1频繁项集生成f候选项集后，加入字符窜数组中，便于下面查询是否生成过此边
int sides_num;		//记录gn中已经生成的边的条数

//int re[size][2];

typedef struct temp_graph_freq	//用于统计每次添加一边后,得到的所有候选图的频繁度
{
	char tf[size*6];
	int count;
	int ckf;	//ckf即 check flag，记录在整理时是否被check过
}tgf;

typedef   int   (*fn_s)[graph_num+1];	//fn系列的矩阵，fn series
typedef   int   (*g_s)[size];		//g系列的矩阵，g series

/********************************************************************************/
void readfreq()                //将每条边的频繁度从txt中读出放入freq矩阵中
{
	int i,j,k,m,n;
	FILE *fp;
	if((fp=fopen("frequency.txt","r"))==NULL)
	{
//		printf("Cannot open frequency.txt!\n");
	}
	for(i=0;i<9126;i++)
	{
		for(j=0;j<4;j++)
		{
			//k=fscanf(fp,"%c",&k);
			k=fgetc(fp);
			if(k>=48&&k<=57)
			{
				k=k-48;
				m=k;
				freq[i][j]=k;
			}
			else
				freq[i][j]=k;
		}
	    n=fgetc(fp);
		if(n>=48&&n<=57)
		{
			n=n-48;
			j--;
			freq[i][j]=m*10+n;
			fgetc(fp);
		}
	}
	fclose(fp);
}

fn_s initial_fn(int (*fn)[graph_num+1])   //fn记录各图频繁n项集的个数，初始化fn为0
{
	for	(int i=0; i<20; i++)
		for	(int j=0; j<=graph_num; j++)
		{		
			fn[i][j]=0;
		}
	return fn;
}

g_s read_graph(int gn, int (*g)[size], int filename1_type)    //读入图的关联矩阵，放入矩阵g[size][size]中
{
	int i,j,k;
	FILE *fp;
	
	i=48+int(gn/100);
	j=48+(gn-int(gn/100)*100)/10;
	k=48+gn-int(gn/100)*100-(j-48)*10;
	
	char filename1[20];

	switch(filename1_type)
	{
	case 1: 
		{
			char filename[20]={i,j,k,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 2: 
		{
			char filename[20]={i,j,k,'n','\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 3:
		{
			int l = 48 + (f-1);
			int m = 48 + seq_f/10;
			int n = 48 + seq_f - seq_f/10*10;
			char filename[20]={i,j,k,'.','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 4:
		{
			int l = 48 + f;
			int m = 48 + seq_tf/10;
            int n = 48 + seq_tf - seq_tf/10*10;
            char filename[20]={i,j,k,'.','t','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	}
	
	char filename2[5]={'.','t','x','t','\0'};
    strcat(filename1,filename2);    //记事本文件名
	
	if((fp=fopen(filename1,"r"))==NULL)
	{
//		printf("Cannot open %s!\n", filename1);
	}
	/*****/
	else
//		printf("Open %s successfully!\n",filename1);
	/*****/
	for(i=0;i<size;i++)
	{
		for(j=0;j<size;j++)
		{
			//k=fscanf(fp,"%c",&k);
			k=fgetc(fp);
			if(k>=48&&k<=57)
			{
				g[i][j]=k-48;
			}
			else
				g[i][j]=k;
		}
	    fgetc(fp);
	}
	fclose(fp);
	return g;
}

g_s cut_unfreq_side(int scut, int (*g)[size])		//剪去图gn中非频繁的边
{   
	int i=0, j=0;
	for(i=0; i<size; i++)	//如果是最后一条边要剪去，则下面的for就不做了，所以这里直接将这列置为零
		g[i][scut] = 0;
//	printf("cut side %d start\n", scut);
	for(j=scut; j<size-1 && g[0][j+1]!=0; j++)
		for(i=0; i<size; i++)
			g[i][j] = g[i][j+1];
//	printf("cut sides over\n");
//	for(j=2; j<size; j++)
//		if(g[0][j]==g[0][j+1] && g[1][j]==g[1][j+1])
//			break;
	for(i=0; i<size; i++)
		g[i][j]=0;
	return g;
}

g_s cut_isolated_dot(int dcut,int (*g)[size])		//剪去孤立点（即与其他点没有边相连）
{
	int i=0, j=0;
	for(j=0; j<size; j++)	//如果是最后一个点要剪去，则下面的for就不做了，所以这里直接将这行置为零
		g[dcut][j] = 0;
	for(i=dcut; i<size-1 && g[i+1][0]!=0; i++)
		for(j=0; j<size; j++)
			g[i][j]=g[i+1][j];
//	for(i=2; i<size; i++)
//		if(g[i][0]==g[i+1][0] && g[i][1]==g[i+1][1])
//			break;
	for(j=0; j<size; j++)
		g[i][j]=0;
	return g;
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

g_s cut_unfreq(int (*g)[size])         //通过访问频繁度表，减去各个图中非频繁的边
{
	int i,j,p,q,flag,temp;
//	size_side=32;
//  size_dot=32;


/*	for(i=0; i<size; i++)
	{
		for(j=0; j<size; j++)
			if(g[i][j]>64)
				printf("%c",g[i][j]);
			else
				printf("%d",g[i][j]);
		printf("\n");
	}			
	getchar();*/
	
	for(j=2; j<size && g[0][j]!=0; j++)	//对于某一边j
	{
		for(i=2; i<size; i++)	//先找到其某一端点
			if(g[i][j]==1)
			{
				p=i;
				break;
			}
		for(i=size-1; i>=2; i--)	//再找到其另一个端点
		    if(g[i][j]==1)
			{
				q=i;
				break;
			}
		temp=freq[((g[0][j]-65)*351)+jcalculate(g[p][0]-97)+(g[q][0]-g[p][0])][3];
//		printf("%d ",temp);
//		/*****/printf("%c%c%c freq is:%d\n",g[0][j],g[p][0],g[q][0],temp);
//		getchar();
        if(temp<sup)	//查询边频繁度表，得到这条边的频繁度，如果小于支持度，则剪去此边
		{
			g = cut_unfreq_side(j,g);		//剪去图gn中不频繁的边j
			if( g[0][j]==0 )
				break;
			else
				j--;
//			size_side--;
		}
//		getchar();
	}

//	printf("cut dots start\n");
//	getchar();
	for(i=2; i<size; i++)		//对于某一点i
	{
		flag=0;		//假设没有边与此点相连
		for(j=2; j<size; j++)
			if(g[i][j]==1)
			{
				flag=1;
				break;
			}
		if(flag==0)		//经过查找，真没有边与此点相连，则此点为孤立点，去掉此点
		{
            g=cut_isolated_dot(i,g);		//剪去孤立点（即与其他点没有边相连）
			if( g[i][0]==0)
				break;
			else
				i--;
//		    size_dot--;
		}
	}

	/****/
/*	for(i=0; i<size; i++)
	{
		for(j=0; j<size; j++)
			if(g[i][j]>64)
				printf("%c",g[i][j]);
			else
				printf("%d",g[i][j]);
		printf("\n");
	}			
	/*****/
	/***////printf("gn in cut%d\n",gn);

	return g;
	
}

void print_graph(int gn, int (*g)[size], int filename1_type)     //输出剪了后的图的关联矩阵g[size][size]到“图号n.txt”
{
	int i,j,k;
	FILE *fp;

////	printf("gn in print is:%d   ",gn);

	i=48+int(gn/100);
	j=48+(gn-int(gn/100)*100)/10;
	k=48+gn-int(gn/100)*100-(j-48)*10;
/*******/
//	printf("gn of print:%d  %c%c%c\n",gn,i,j,k);
	/****/
	char filename1[20];

	switch(filename1_type)
	{
	case 1: 
		{
			char filename[20]={i,j,k,'n','\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 2:
		{
			int m = 48 + seq_f1/10;
            int n = 48 + seq_f1 - seq_f1/10*10;
            char filename[20]={i,j,k,'.','f','1','.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 3:
		{
			int l = 48 + f;
			int m = 48 + seq_tf/10;
            int n = 48 + seq_tf - seq_tf/10*10;
            char filename[20]={i,j,k,'.','t','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 4:
		{
			int l = 48 + f;
			int m = 48 + seq_f/10;
            int n = 48 + seq_f - seq_f/10*10;
            char filename[20]={i,j,k,'.','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 5:
		{
			int l = 48 + f;
			int m = 48 + s_f/10;
            int n = 48 + s_f - s_f/10*10;
            char filename[20]={'f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	}
	
	char filename2[5]={'.','t','x','t','\0'};
	strcat(filename1,filename2);

	fp = fopen(filename1,"w");
	if(fp!=NULL)
	{
//		printf("%s has been created!\n", filename1);
	}
	else
	{
//		printf("Cannot creat %s\n",filename1);
	}
    for(i=0;i<size;i++)
	{
		for(j=0;j<size;j++)
		{
//			if(i>=size_dot||j>=size_side)
//				fprintf(fp,"0");
//			else
//			{
				k=g[i][j];
			    if(k>64)
				{
					fprintf(fp,"%c",k);
				}
			    else
				fprintf(fp,"%d",k);
//			}
		}
		fputc('\n',fp);
	}
	fclose(fp);
}

void prune()                  //将原图“图号.txt”剪去非频繁边，得到“图号n.txt”
{
	int gn; 
	gn=1;
	while(gn <= graph_num)
	{
		int og[size][size];         //将未剪枝的图从“图号.txt”中读出放入矩阵g[size][size]中
		int (*g)[size]=og;
		g=read_graph(gn,g,1);
		/********/
//		getchar();
//		printf("gn before cut %d\n",gn);
		/*******/
		g=cut_unfreq(g);              //将各个图的非频繁边去掉
//		printf("gn after cut %d\n",gn);
		print_graph(gn,g,1);              //输出剪了后的图的关联矩阵g[size][size]到“图号n.txt”
		gn++;
	}
}

int get_gn_sides_num(int (*g)[size])		//获取gn的边数   
{
	int gn_f1_count=0,j;
    for(j=2;j<size;j++)
	{
		if(g[0][j]!=0)
			gn_f1_count++;
   	}
	return gn_f1_count;
}

g_s gn_side_to_array(int (*g_f1)[size], int (*g)[size])     //将gn的第seq_f1条边转化到图矩阵g_f1中存放
{
	int i,j,p,q;
	int side = seq_f1 + 1;
	for(i=0;i<size;i++)        //先将g_f1初始化为0
		for(j=0;j<size;j++)
		{
			g_f1[i][j]=0;
		}
    for(i=2;i<size;i++)		//找到这条边的一个端点
		if(g[i][side]==1)
		{
			p=i;
			break;
		}
	for(i=size-1;i>=2;i--)	//找到这条边的另一个端点
		if(g[i][side]==1)
		{
			q=i;
			break;
		}
	g_f1[0][2]=g[0][side];
	g_f1[1][2]=g[1][side];
	g_f1[2][0]=g[p][0];
	g_f1[2][1]=g[p][1];
    g_f1[3][0]=g[q][0];
	g_f1[3][1]=g[q][1];
	g_f1[2][2]=1;
	g_f1[3][2]=1;
	return g_f1;
}

fn_s print_freq_one(int (*fn)[graph_num+1])    //输出各图的频繁一项集到“图号.f1.第几个.txt”
{
	int gn=1;
	while( gn<=graph_num )         //从图一开始，输出各图的频繁一项集
	{
		int og[size][size];
		int (*g)[size]=og;
        g=read_graph(gn,g,2);       //读入“图号n.txt”，放入g[size][size]
		int gn_f1_count = get_gn_sides_num(g);     //获取gn的边数
		fn[1][gn] = gn_f1_count;		//将gn的边数放入项集统计数组
		seq_f1 = 1;				 //标记第几个（“图号.f1.第几个.txt”）
        while ( gn_f1_count != 0 )
		{
			int og_f1[size][size];
			int (*g_f1)[size]=og_f1;
			g_f1 = gn_side_to_array(g_f1,g);	//将gn的第seq_f1条边转化到图矩阵g_f1中存放
			print_graph(gn, g_f1, 2);    //输出图gn的第seq_f1个频繁一项集（边）
			gn_f1_count--;
			seq_f1++;
		}
		gn++;
	}
	return fn;
}

int get_last_f_num(int (*fn)[graph_num+1], int last_f)             //看有f项频繁项集生成吗
{
	for(int j=1;j<=graph_num;j++)
		if(fn[last_f][j]!=0)
			return 1;
	return 0;	
}

g_s modify_g(int (*g_last_f)[size], int (*g)[size], int (*g_temp)[size])
//将g_lasf_f中已有的边在参照矩阵g中删掉
{
	int gl_firstj;
	int i;
	int j;
	int n;	//m,n分别用于g矩阵的行列

	for (i=0; i<size; i++)		//初始化temp阵为0
		for (j=0;j<size; j++)
		{
			g_temp[i][j] = 0;
		}

	for(gl_firstj=2; gl_firstj<size; gl_firstj++)	//找到g_lst_f的第一条边在g中的位置
	{
		if( g[0][gl_firstj] == g_last_f[0][2] )
			break;
	}
	
	for(i=2; i<size; i++)		//将参照阵的点集传给g_temp阵
	{
		g_temp[i][0] = g[i][0];
		g_temp[i][1] = g[i][1];

	}
	
	int temp_flag=2;		//在g_last_f中没有出现的边在temp阵中存放位置从2开始
	for(j=gl_firstj; j<size; j++)
	{
		int flag = 0;		//初始认为g的这条边不在g_last_f中
		for(n=2; n<size; n++)
			if(g[0][j] == g_last_f[0][n] && g[1][j] == g_last_f[1][n])
			{
				flag = 1;
				break;
			}
		if(flag==0)		//经过上面查询，如果这条边不在g_last_f中，则可以放入temp阵中
		{
			for(i=0; i<size; i++)
			{
				g_temp[i][temp_flag] = g[i][j];
			}
			temp_flag++;
		}
	}
	/***
	printf("after modify, g_last_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_last_f[i][j]>64)
					printf("%c",g_last_f[i][j]);
				else
					printf("%d",g_last_f[i][j]);
			printf("\n");
		}
	printf("after modify, g_temp is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_temp[i][j]>64)
					printf("%c",g_temp[i][j]);
				else
					printf("%d",g_temp[i][j]);
			printf("\n");
		}
	/****/
	return g_temp;
}

int dot_position(int r, int (*g)[size], int (*g_f)[size])
{
	int i,k,flag=0;		//flag初始为0表示假设g_f中没有这个端点
	/***
	printf("when dot position, g_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
				if(g_f[i][j]>64)
					printf("%c",g_f[i][j]);
				else
					printf("%d",g_f[i][j]);
			printf("\n");
		}
	printf("when dot position, g is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
				if(g[i][j]>64)
					printf("%c",g[i][j]);
				else
					printf("%d",g[i][j]);
			printf("\n");
		}
	printf("want to put %c%d of g into g_last_f\n",g[r][0],g[r][1]);
	/***/
//	/***/getchar();
	for(i=2; i<size; i++)		//寻找端点的合适位置
	{
		if(g_f[i][0] >= g[r][0] || g_f[i][0]==0)
		{
			break;
		}
	}
	/***
	printf("i=%d\n",i);
	getchar();
	/****/
	if(g_f[i][0] == g[r][0])	//如果g_f中有此标号的点
	{
		k=i;
		do
		{
			if(g_f[k][0]==g[r][0] && g_f[k][1]==g[r][1])	//查看是否有相同下标的点
			{
				flag=1;
				break;
			}
			k++;
		}while(g_f[k][0] == g_f[k-1][0]);
	}
    if(flag==0)
		return i;		//如果没有此相同下标的此点，则在g_f中插入此端点
	else
		return -k;		//如果已经有此相同下标的此点，则须在g_f的点边交接处改为1
}

g_s gl_to_gf(int n, int (*g)[size], int (*g_last_f)[size], int (*g_f)[size])
//图g_last_f加入参照表的第n列边，并将产生的新图放入g_f中
{
	int p,q,k;
	int i,j;
	int m;
	/***
	printf("before some functions and dot_position, g is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g[i][j]>64)
					printf("%c",g[i][j]);
				else
					printf("%d",g[i][j]);
			printf("\n");
		}
	printf("before some functions and dot_position, g_last_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_last_f[i][j]>64)
					printf("%c",g_last_f[i][j]);
				else
					printf("%d",g_last_f[i][j]);
			printf("\n");
		}
	/***/
	for(i=0;i<size;i++)		//先将g_f初始化得和g_last_f一样
		for(j=0;j<size;j++)
		{
			g_f[i][j] = g_last_f[i][j];
		}
	for(m=2;m<size;m++)		//找到要加的这条边的一个端点，放入p中
		if(g[m][n]==1)
		{
			p=m;
			break;
		}
	for(m=size-1;m>=2;m--)		//找到要加的这条边的另一个端点，放入q中
		if(g[m][n]==1)
		{
			q=m;
			break;
		}
    for(j=2; j<size; j++)		//寻找这条边要在g_f中插入的位置
	{
		if( g_f[0][j]>=g[0][n] || g_f[0][j]==0 )
			break;
	}
	
	int temp = j;		//此边要插在g_f的第temp列
	for(j=size-2; j>=temp; j--)		//将temp这一列开始通通往后移动一列，让出temp这一列
		for(i=0;i<size;i++)
		{
			g_f[i][j+1]=g_f[i][j];
		}
	for(i=0; i<size; i++)	//先将g_f这一列清0一下，防止以前这一列有1的与下面赋1的混淆一起
		g_f[i][temp] = 0;
	g_f[0][temp] = g[0][n];		//在temp列插入边的标号
	g_f[1][temp] = g[1][n];
	//上面已经将边号插入g_f,下面要将点的标号插入g_f
//	/***/printf("start dot position:"); getchar();
	i = dot_position(p,g,g_f);		//寻找其中一个点的插入位置
	if(i<0)
	{
		g_f[-i][temp]=1;		//如果已经有此相同下标的此点，则须在g_f的点边交接处改为1
	}
	else			//如果没有此相同下标的此点，则在g_f中插入此端点
	{
		for(int k=size-2;k>=i;k--)
		{
			for(j=0;j<size;j++)
			{
				g_f[k+1][j]=g_f[k][j];
			}
		}
		for(j=0;j<size;j++)	//以前没有g[p][0]g[p][1]这一点
			g_f[i][j]=0;
		g_f[i][0]=g[p][0];
		g_f[i][1]=g[p][1];
		g_f[i][temp]=1;
	}
	
	i=dot_position(q,g,g_f);		//寻找另一个端点的插入位置
	if(i<0)
	{
		g_f[-i][temp]=1;		//如果已经有此相同下标的此点，则须在g_f的点边交接处改为1
	}
	else		//如果没有此相同下标的此点，则在g_f中插入此端点
	{
		for(k=size-2;k>=i;k--)
		{
			for(j=0;j<size;j++)
			{
				g_f[k+1][j]=g_f[k][j];
			}
		}
		for(j=0;j<size;j++)	//以前没有g[q][0]g[q][1]这一点
			g_f[i][j]=0;
		g_f[i][0]=g[q][0];
		g_f[i][1]=g[q][1];
		g_f[i][temp]=1;
	}
	/***
	printf("g_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_f[i][j]>64)
					printf("%c",g_f[i][j]);
				else
					printf("%d",g_f[i][j]);
			printf("\n");
		}
	/***/
	return g_f;
}

int compik(int i, int k, int (*g_f)[size])		//比较第i行与k行的第一条边的数量
{
	int ci,ck;		//c即count，用于记录i行和k行的同一类边的条数
	int j=2;
	int r;
	while(g_f[0][j]!=0 && j<size)
	{
		ci=0;
		ck=0;
		r=j;	//先从第一条边开始
		do
		{
			if(g_f[i][r]==1)
				ci++;
			if(g_f[k][r]==1)
				ck++;
			r++;
		}while(g_f[0][r]==g_f[0][j]);	
		if(ci!=ck)		//如果比较出了数量多少，则停止比较
			break;
		j=r;		//否则就从下个类别的边开始比较
	}
	if(ci==ck)
		return 0;        //i行与k行各种边数都一样多
	if(ci>ck)
		return -1;      //i行比k行在第一个不相同边的数量上多
	if(ci<ck)
		return 1;        //i行比k行在第一个不相同边的数量上少
}

int compikc(int i, int k, int (*g_f)[size])	//比较第i行与k行的从左到右组成的二进制值的大小
{
	int j=2;	//从第一条边开始
	while( g_f[0][j]!=0 && j<size )
	{
		if( g_f[i][j] < g_f[k][j] )
			return 1;
		if( g_f[i][j] > g_f[k][j] )
			return 0;
		j++;
	}
}	

int compjk(int j, int k,  int (*g_f)[size])		//比较第j列与k列的由上往下组成的二进制值的大小
{
	int i=2;	//从第一个点开始
	while( g_f[i][0]!=0 && i<size)
	{
		if( g_f[i][j] < g_f[i][k] )		//如果k列由上往下组成的二进制值比j列的大
		{
			return 1;
		}
		if( g_f[i][j] > g_f[i][k] )		//如果k列由上往下组成的二进制值比j列的小
		{
			return 0;
		}

		i++;
	}
}

g_s standard_before(int (*g_f)[size])
//将g_f还原为原图中的点和边的顺序，为的是能够在标准化后得到的矩阵的唯一性
{	
	int i,k,j,temp;

	for(i=2; g_f[i][0]!=0 && i<size; i++)	//先做行还原，将同类点中，序号小的放前面
		for(k=i+1; g_f[k][0]==g_f[i][0] && k<size; k++)
		{
			if( g_f[k][1] < g_f[i][1] )	//同一点中，如果k行的序号比i行的小，则调换k行和i行
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
		}

	for(j=2; g_f[0][j]!=0 && j<size; j++)	//再做列还原，将同类边中，序号小的放前面
		for(k=j+1; g_f[0][k]==g_f[0][j] && k<size; k++)
		{
            if( g_f[1][k] < g_f[1][j] )	//同一边中，如果k列的序号比i列的小，则调换k列和i列
				for(i=0; i<size; i++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[i][k];
					g_f[i][k] = temp;
				}
		}

	return g_f;
}

g_s standard_gf(int (*g_f)[size])       //加了边后得到了g_f，将g_f标准化
{
	int i,k,j,temp,flag,flagc;
	g_f = standard_before(g_f);
	/***
	printf("before standard, g_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_f[i][j]>64)
					printf("%c",g_f[i][j]);
				else
					printf("%d",g_f[i][j]);
			printf("\n");
		}
	/***/
	for(i=2; g_f[i][0]!=0 && i<size; i++) //做行变换比较,在同一类点中，将第一条数量不相同边数量多的点放前面
		for(k=i+1; g_f[k][0]==g_f[i][0] && k<size; k++)	
		//对于第i行，从它下面一行开始，如果标号相同，则比较第一条边的数量
		{
			flag=0;		//假设第i行的第一条边的数量比k行的多
			flag=compik(i,k,g_f);		//调用比较函数，比较第i行与k行的第一条边的数量
			if(flag==1)		//如果k行的第一条不相同边的数量比i多，则互换i行和k行
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
		}

    /***
	printf("after 行变换, g_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_f[i][j]>64)
					printf("%c",g_f[i][j]);
				else
					printf("%d",g_f[i][j]);
			printf("\n");
		}
	/***/
	
	for(i=2; g_f[i][0]!=0 && i<size; i++)
		for(k=i+1; g_f[k][0]==g_f[i][0] && k<size; k++)
		{	
			flag=0;
			flag=compik(i,k,g_f);
			if(flag==0)	//如果两行的边数相同，则比较其组成的二进制数的大小，大的排前面
			{
				flagc=compikc(i,k,g_f);	//flagc 即 记录哪一行的数字组成二进制大
				if(flagc==1)		//如果k行的组成的二进制比i行大，则互换i行和k行
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
			}
		}

	for(j=2; g_f[0][j]!=0 && j<size; j++)	//做列变换比较，同一类边中，将由上往下组成的二进制值大的边放前面
		for(k=j+1; g_f[0][k]==g_f[0][j] && k<size; k++)
		//对于第j列，从它下面一列开始，如果标号相同，则比较由上往下组成的二进制值
		{
			flag=0;		//假设第i列的由上往下组成的二进制值比k列的大
			flag=compjk(j,k,g_f);		//调用比较函数，比较第j列与k列的由上往下组成的二进制值的大小
            if(flag==1)		//如果k列由上往下组成的二进制值比j列的大，则互换j列和k列
				for(i=0; i<size; i++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[i][k];
					g_f[i][k] = temp;
				}
		}

	/***
	printf("after 列变换, g_f is:\n"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_f[i][j]>64)
					printf("%c",g_f[i][j]);
				else
					printf("%d",g_f[i][j]);
			printf("\n");
		}
	/***/

	return g_f;
}

int check_gf(int (*g_f)[size])
{
	char gf[2*size];
	int i=0, j=2;
	for(i=0; g_f[0][j]!=0 && j<size; i=i+2)		//将gn中此次生成的候选图g_f变为字符串gf
	{
		gf[i] = g_f[0][j];
		gf[i+1] = g_f[1][j]+48;
		j++;
	}
	gf[i] = '\0';
	for(i=0; i<sides_num; i++)		//与之前的串集进行比较，如果以前有过则返回1，否则返回0
		if( strcmp(gn_sides[i], gf) == 0 )
			return 1;
	return 0;
}

void add_gf(int (*g_f)[size])
{
	int i=0, j=2;
	for(i=0; g_f[0][j]!=0 && j<size; i=i+2)		//将gn中此次生成的候选图g_f变为字符串放入gn_sides集中
	{
		gn_sides[sides_num][i] = g_f[0][j];
		gn_sides[sides_num][i+1] = g_f[1][j]+48;
		j++;
	}
	gn_sides[sides_num][i] = '\0';
	sides_num++;
}

void add_edge(int gn, int (*g_last_f)[size], int (*g)[size])
{
	int og_f[size][size];
	int (*g_f)[size]=og_f;	//用来存放由原图g_last_f添加一条频繁边后的图（候选集）
	int og_temp[size][size];		
	int (*g_temp)[size]=og_temp;	//定义一个temp矩阵，存放在g_last_f中没有的边
	int i;
	int j;
	int m;
	int n;

//	/***/printf("before modify"); getchar();

	g_temp = modify_g(g_last_f, g, g_temp);		//将g_lasf_f中已有的边在参照矩阵g中删掉
	g = g_temp;
//	/***/getchar();
	/***
	printf("after modify, g_temp become g, and it is:"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g[i][j]>64)
					printf("%c",g[i][j]);
				else
					printf("%d",g[i][j]);
			printf("\n");
		}
	printf("after modify, g_last_f is:"); getchar();
	for(i=0; i<size; i++)
		{
			for(j=0; j<size; j++)
				if(g_last_f[i][j]>64)
					printf("%c",g_last_f[i][j]);
				else
					printf("%d",g_last_f[i][j]);
			printf("\n");
		}
	/****/
//	/***/printf("Next do the f-1 to f work\n"); getchar();

	for(i=2; i<size; i++)		//提取出g_last_f中的一个点
		for(m=2; m<size; m++)			//在g表中进行查询,是否拥有含有此点的边
			if(g_last_f[i][0]==g[m][0] && g_last_f[i][1]==g[m][1])		//先在g中找到此点
				for(n=2; n<size; n++)
					if(g[m][n]==1)
					{
//						/***/printf("before gl to gf"); getchar();
						g_f = gl_to_gf(n, g, g_last_f, g_f);	//如果以前没有加过，则加入此边
//						/***/getchar();
//						/***/printf("after gl to gf, before standard"); getchar();
						/***
						printf("before standard, g_f is:"); getchar();
						for(int ii=0; ii<size; ii++)
						{
							for(int jj=0; jj<size; jj++)
							if(g_f[ii][jj]>64)
								printf("%c",g_f[ii][jj]);
							else
								printf("%d",g_f[ii][jj]);
							printf("\n");
							}
						/***/
						g_f = standard_gf(g_f);		//加了边后得到了g_f，将g_f标准化
//						/***/printf("after standard"); getchar();
						/***
						printf("after standard, g_f is:"); getchar();
						for(ii=0; ii<size; ii++)
						{
							for(int jj=0; jj<size; jj++)
							if(g_f[ii][jj]>64)
								printf("%c",g_f[ii][jj]);
							else
								printf("%d",g_f[ii][jj]);
							printf("\n");
							}
						/***/

						int flag;
//						/***/printf("before check"); getchar();
						flag = check_gf(g_f);	//查询gn_sides集，得知此项集在gn中是否已经有了
//						printf("before print, flag=%d",flag); getchar();
						if(flag == 0)	//如果没有过，则加入gn_sides串集，并将其以“图号.tf?.第几个.txt”格式打印出来
						{
							add_gf(g_f);		//将此边加入gn的sides集
							seq_tf++;
							print_graph(gn, g_f, 3);     //以序号seq_tf输出gg，输出名为：“图号.tf?.第几个.txt”							
						}
//						/***/printf("after print,i=%d,m=%d,n=%d",i,m,n); getchar();
					}
//	/***/printf("opened 00%d.f%d.0%d",gn,f-1,seq_f); getchar();
//    return;
}

fn_s generate_gn_f(int gn, int (*fn)[graph_num+1], int (*t_fn)[graph_num+1])
//由gn的f-1频繁项集，生成gn的f候选项集
{
//	gnf=fn[f-1][gn];      //gnf记下图gn有多少个频繁项
	int og[size][size];
	int (*g)[size]=og;
	g = read_graph(gn,g,2);
	//读入“图号n.txt”，放入g[size][size],对于g_last_f[size][size]可以查询g[size][size]，从而以有公共点来添加边

	seq_tf=0;		//对于每个图，将其记录候选集个数的变量初始化为0

	sides_num = 0;		//准备由f-1项集生成f候选项集，则边串数初始为0个

	for( seq_f=1; seq_f <= fn[f-1][gn]; seq_f++)
	{
		int og_last_f[size][size];
		int (*g_last_f)[size]=og_last_f;
		g_last_f = read_graph(gn, g_last_f,3);		//读入图gn的第seq_f个f-1频繁项，放入g_last_f矩阵中
		add_edge(gn,  g_last_f, g);		
		//将图gn中f-1频繁项集添加一条边，查询gn_sides表，如未加入过，则此为f候选项集，并以“图号.tf?.第几个.txt”格式输出
	}
	t_fn[f][gn] = seq_tf;
	return t_fn;
}

tgf *initial_gc(tgf gc[temp_graph_num])	//初始化结构体数组gc，将候选项出现次数初始化为0
{
	for(int i=0; i<temp_graph_num; i++)
	{
		gc[i].count=0;
		gc[i].ckf=0;	//初始为0，表示没有被check过
	}
	return gc;
}

char *string_tf(int gn, char gntf[6*size])
//读入图gn的seq_tf个候选项放入矩阵g_f，将矩阵压缩成字符串放入gntf中
{
	int og_f[size][size];
	int (*g_f)[size]=og_f;
//	/***/printf("f=%d	gn=%d	seq_tf=%d",f,gn,seq_tf);	getchar();
	g_f = read_graph(gn, g_f, 4);
	int gntfi;	//gntf的下标
	gntfi = 0;
	int i,j,p,q;
	gntf[gntfi]=gn+96;
	//将图序号写入字符串头，防止下面在统计时，出现同一gn中，转化为字符串相同，就记入统计的情况
	gntfi++;
	for(j=2; g_f[0][j]!=0 && j<size; j++)
	{
		for(i=2;i<size;i++)		//找到这条边的一个端点
		if(g_f[i][j]==1)
		{
			p=i;
			break;
		}
		for(i=size-1;i>=2;i--)	//找到这条边的另一个端点
		if(g_f[i][j]==1)
		{
			q=i;
			break;
		}
		gntf[gntfi] = g_f[0][j];	//先放入边的编号
		gntf[gntfi+1] = g_f[p][0];	//再放入一个端点
		gntf[gntfi+2] = p + 96;	//放入此点的位置（将位置转化为小写字母，因为位置可能有几十，一个数字不能表示）
		gntf[gntfi+3] = g_f[q][0];	//放入另一个端点
		gntf[gntfi+4] = q + 96;	//放入另一个点的位置
		gntfi = gntfi + 5;
	}
	gntf[gntfi] = '\0';
	/***
	printf("When make g_f to string, it is:\n");
	for(j=0; gntf[j]!='\0'; j++)
		printf("%c",gntf[j]);
	getchar();
	/***/
	return gntf;
}

int check_gn_tempgraph(tgf gc[temp_graph_num], int gci, char gntf[6*size])
//检查字符串gntf是否在gc中有记录，如果有，则返回所在单元号，如果没有，返回-1
{
	int i;
	int j;
	/***
	printf("before check, gntf is:\n");
	for(i=0; gntf[i]!='\0'; i++)
		printf("%c",gntf[i]);
	printf("\n");
	getchar();
	/***/

	for(i=0; i<gci; i++)
		if( strcmp(gc[i].tf, gntf) == 0 )
			return -2;
		//如果出现同图中的两个候选f图化成字符串相同，则返回-2，统计方面不做任何操作
/*	tgf tgc[temp_graph_num];
	char tgntf[6*size];
	for(i=0; i<gci; i++)	//在统计库中，将每个字符串的第一个去掉，即将gn抹去
	{
		for(j=1; gc[i].tf[j]!='\0'; j++)
			tgc[i].tf[j-1]=gc[i].tf[j];
		tgc[i].tf[j-1]='\0';
	}
	for(j=1; gntf[j]!='\0'; j++)
		tgntf[j-1]=gntf[j];
	tgntf[j-1]='\0';	//在匹对串中，将字符串的第一个去掉，即将gn抹去
	for(i=0; i<gci; i++)	//再次做比较操作，因为此前已经淘汰了同图同串的情况，所以此次是异图同串的情况
		if( strcmp(tgc[i].tf, tgntf) == 0 )
			return i;
*/	
	return -1;	//字符串gntf在库中没有出现过，则要加入库中
}

tgf *add_gn_tempgraph(tgf gc[temp_graph_num], int gci, char gntf[6*size])
{
	int i;
	for(i=0; gntf[i]!='\0'; i++)
		gc[gci].tf[i] = gntf[i];
	gc[gci].tf[i] = '\0';
	gc[gci].count = 1;
	/***
	printf("Test add gn, gntf is:\n");
	for(i=0; gntf[i]!='\0'; i++)
		printf("%c",gntf[i]);
	printf("\n");
	printf("Test add gn, gc[%d].tf is:\n", gci);
	for(i=0; gc[gci].tf[i]!='\0'; i++)
		printf("%c",gc[gci].tf[i]);
	printf("\n");
	getchar();
	/***/

	return gc;
}

/*tgf *add_count(tgf gc[temp_graph_num], int gci, int flag)
{
	gc[flag].count++;
	return gc;
}*/

tgf *join_common_string(tgf gc[temp_graph_num], tgf gcn[temp_graph_num], int gci)
{
	int i,j;
	int p;
	int gcni;
	gcni=0;
	char tft[6*size];

	for(i=0; i<gci; i++)	//从第一个串开始扫描
	{
		if(gc[i].ckf==1)	//如果此串已经被记录进gcn，则跳到下一个串
			continue;
		for(j=1; gc[i].tf[j]!='\0'; j++)	//取出gcn中的第i行的串，将除去gn后的串放入gc中
			gcn[gcni].tf[j-1]=gc[i].tf[j];
		gcn[gcni].tf[j-1]='\0';
		gcn[gcni].count=1;
		for(p=i+1; p<gci; p++)
		{
			for(j=1; gc[p].tf[j]!='\0'; j++)	//取出gcn中的第p行的串，将除去gn后的串放入tft中
				tft[j-1]=gc[p].tf[j];
			tft[j-1]='\0';
			if( strcmp( tft, gcn[gcni].tf)==0 )
			{
				gcn[gcni].count++;
				gc[p].ckf=1;
			}
		}
		gcni++;
	}

	gcn[0].ckf=gcni;
	return gcn;
}

int inquire_gc(tgf gc[temp_graph_num], int gci, char gntf[6*size])
{
	int i,j;
	char tgntf[6*size];
	for(j=1; gntf[j]!='\0'; j++)
		tgntf[j-1]=gntf[j];
	tgntf[j-1]='\0';	//在匹对串中，将字符串的第一个去掉，即将gn抹去
	for(i=0; i<gci; i++)
		if( strcmp(gc[i].tf, tgntf) == 0 )
			break;
	return gc[i].count;
}

fn_s choosefreq(int (*fn)[graph_num+1], int (*t_fn)[graph_num+1])
//从候选f项集中选出频繁的，以“图号.f?.第几个.txt”格式打印出来，同时将各图频繁项个数记入fn
{	
	int gci;
	gci = 0;	//gc阵的下标，初始为0
	int gn;
	int i,j;
	int n;	//m,n为g专用的，标注行和列
	int position;
	tgf ogc[temp_graph_num];
	tgf *gc=ogc;	//gc用来记录含有异图同串未合并的图统计表
	tgf ogcn[temp_graph_num];
	tgf *gcn=ogcn;	//gcn表示gc new,即经过整理后的gc，放如gcn中，为正式的图统计表

	gc = initial_gc(gc);	//gc结构体数组用来存放某一候选项压缩成的字符串，只去除了同图同串的现象
	gcn = initial_gc(gcn);

	for(gn=1; gn<=graph_num; gn++)	//先将生成候选f项集进行记入频繁度矩阵，从图一开始
		for(seq_tf=1; seq_tf<=t_fn[f][gn]; seq_tf++)
		{
			int flag = -1;	//假设gn中的第seq_tf个候选项在gc阵中没有出现过
			char ogntf[6*size];
			char *gntf=ogntf;	//存放gn中的第seq_tf个候选项压缩成的字符串
			gntf = string_tf(gn, gntf);	//记得最后加'\0'
//			/***/getchar();
			flag = check_gn_tempgraph(gc, gci, gntf);	//flag返回-1或者在gc中出现ti的行或者-2
			/***
			printf("after check, gntf is:\n");
			for(i=0; gntf[i]!='\0'; i++)
				printf("%c",gntf[i]);
			printf("\n");
			getchar();
			/***/
			if(flag == -1)
			{
				gc = add_gn_tempgraph(gc, gci, gntf);//如果以前没有出现过，则放入gc阵的第gci个单元
				gci++;
			}
//			if(flag >= 0)
//				gc = add_count(gc, gci, flag);		//否则在以前出现过的单元，将count值加1
		}
//	/***/getchar();
	/***
	if(f==2)
	{
		printf("f=2, original table:\n");
		getchar();
		for(i=0; i<gci; i++)
		{
			for(j=0; gc[i].tf[j]!='\0'; j++)
				printf("%c",gc[i].tf[j]);
//			printf("\t%d",gc[i].count);
			printf("\n");
		}
	}
	/***/

	gcn = join_common_string(gc, gcn, gci);	//将gc进行整理，去除异图同串的情况
	gci=gcn[0].ckf;
	for(i=0; i<gci; i++)
	{
		strcpy(gc[i].tf, gcn[i].tf);
		gc[i].count=gcn[i].count;
	}

	/***
	if(f==2)
	{
		printf("f=2, modified table:\n");
		getchar();
		for(i=0; i<gci; i++)
		{
			for(j=0; gc[i].tf[j]!='\0'; j++)
				printf("%c",gc[i].tf[j]);
			printf("\t%d",gc[i].count);
			printf("\n");
		}
	}
	/***/

	for(gn=1; gn<=graph_num; gn++)	//重新扫描候选f项集，将频繁项以“图号.f?.第几个.txt”格式打印出来，从图一开始
	{
		seq_f = 0;
		for(seq_tf=1; seq_tf<=t_fn[f][gn]; seq_tf++)
		{
			int tf_sup;
			char ogntf[6*size];	
			char *gntf=ogntf;	//存放gn中的第seq_tf个候选项压缩成的字符串
			gntf = string_tf(gn, gntf);	//记得最后加'\0'
			tf_sup = inquire_gc(gc, gci, gntf);
			/***
			if(gn==3)
			{
				printf("graph 003's f2 choose course:\n");
				getchar();
				for(j=0; gntf[j]!='\0'; j++)
					printf("%c",gntf[j]);
				printf("\t%d", tf_sup);
				printf("\n");
			}
			/***/
			if(tf_sup >= sup)
			{
				seq_f++;
				int og[size][size];
				int (*g)[size]=og;
				g = read_graph(gn, g, 4);
				print_graph(gn, g, 4);
			}
		}
		fn[f][gn] = seq_f;
	}
	/***
	printf("before f%d, the table is\n",f);
	getchar();
	for(i=0; i<gci; i++)
	{
		for(j=0; gc[i].tf[j]!='\0'; j++)
			printf("%c",gc[i].tf[j]);
		printf("\t%d",gc[i].count);
		printf("\n");
	}
	getchar();
	/***/
	s_f=0;	//按序号s_f打印出频繁度为f时频繁项集
	for(i=0; i<gci; i++)
	{
		int g[size][size]={0};
		if(gc[i].count>=sup)
		{
			n=2;
			for(j=0; gc[i].tf[j]!='\0'; j=j+5)	//将字符串还原为
			{
				g[0][n]=gc[i].tf[j];
				position=gc[i].tf[j+2]-96;
				g[position][0]=gc[i].tf[j+1];
				g[position][n]=1;
				position=gc[i].tf[j+4]-96;
				g[position][0]=gc[i].tf[j+3];
				g[position][n]=1;
				n++;
			}
			s_f++;
			print_graph(0, g, 5);
		}
	}
	return fn;
}

void remove_graph(int gn, int filename1_type)
{
	int i,j,k;


	i=48+int(gn/100);
	j=48+(gn-int(gn/100)*100)/10;
	k=48+gn-int(gn/100)*100-(j-48)*10;

	char filename1[20];

	switch(filename1_type)
	{
	case 1: 
		{
			char filename[20]={i,j,k,'n','\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 2:
		{
			int m = 48 + seq_f1/10;
            int n = 48 + seq_f1 - seq_f1/10*10;
            char filename[20]={i,j,k,'.','f','1','.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 3:
		{
			int l = 48 + f;
			int m = 48 + seq_tf/10;
            int n = 48 + seq_tf - seq_tf/10*10;
            char filename[20]={i,j,k,'.','t','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 4:
		{
			int l = 48 + f;
			int m = 48 + seq_f/10;
            int n = 48 + seq_f - seq_f/10*10;
            char filename[20]={i,j,k,'.','f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	case 5:
		{
			int l = 48 + f;
			int m = 48 + s_f/10;
            int n = 48 + s_f - s_f/10*10;
            char filename[20]={'f',l,'.',m,n,'\0'};
			for(i=0; filename[i]!='\0'; i++)
				filename1[i] = filename[i];
			filename1[i] = '\0';
			break;
		}
	}
	
	char filename2[5]={'.','t','x','t','\0'};
	strcat(filename1,filename2);

	remove(filename1);
}

void romove_tempfile(int (*fn)[graph_num+1], int (*t_fn)[graph_num+1], int fm)
{
	int gn;
	for(gn=1; gn<=graph_num; gn++)
	{
		remove_graph(gn, 1);	//删除“图号n.txt”系列
	}
	for(f=1; f<fm; f++)
		for(gn=1; gn<=graph_num; gn++)
			for(seq_f=fn[f][gn]; seq_f!=0; seq_f--)
				remove_graph(gn, 4);
	for(f=1; f<fm; f++)
		for(gn=1; gn<=graph_num; gn++)
			for(seq_tf=t_fn[f][gn]; seq_tf!=0; seq_tf--)
				remove_graph(gn, 3);
}

int main()
{
    readfreq();                   //将每条边的频繁度从txt中读出放入freq矩阵中
	
	int fm;	//记录最多产生到（候选）fm-1项集的文件

	int ofn[20][graph_num+1];
	int (*fn)[graph_num+1] = ofn;      //fn记录各图频繁n项集的个数

	int ot_fn[20][graph_num+1];		//temp_fn记录候选n项集的个数
	int (*t_fn)[graph_num+1] = ot_fn;

	double t;

	fn = initial_fn(fn);            //fn记录各图频繁n项集的个数，初始化fn为0
	t_fn = initial_fn(t_fn);

	prune();                      //将原图“图号.txt”剪去非频繁边，得到“图号n.txt”
	
	t=(double)clock();
	
	fn = print_freq_one(fn);         //输出各图的频繁一项集到“图号.f1.第几个.txt”
	
	int gn;
	f = 2;                           //从生成频繁二项集（边）开始
	int last_f_num = get_last_f_num(fn,f-1);	//检测有生成频繁一项集吗？
//	/***/printf("frequent 1 has been made\n"); getchar();
	while(last_f_num==1)			//如果last_f_num==0,则表明上次无频繁项集生成，则结束
	{
		for(gn=1; gn<=graph_num; gn++)	//生成频繁f项集时，从图一开始
			if(fn[f-1][gn]!=0)			//如果图gn没有频繁f-1项集，则就不要对图gn添加生成f项集
			{
				t_fn = generate_gn_f(gn, fn, t_fn);		//由gn的f-1频繁项集，生成gn的f候选项集
//				fn[f][gn]=seq2;
			}
//		/***/printf("Temp frequent %d graph has made!\n",f); getchar();
		/***
		for(int i=1; i<3; i++)
		{	for(int j=1; j<=graph_num; j++)
				printf("%d", t_fn[i][j]);
			printf("\n");
		}
		getchar();
		/***/
printf("sdfjpsf\n");
		fn = choosefreq(fn, t_fn);
printf("shengyan\n");
		//从候选项集“图号.tf?.第几个.txt”中挑选出频繁项集，得到“图号.f?.第几个.txt”
		//同时要将seq3放入fn[f][gn]中
		last_f_num = get_last_f_num(fn,f);
		f++;
//		/***/printf("after %d frequent items, f=%d and last_f_num=%d",f-1,f,last_f_num); getchar();
//		/***/printf("Frequent %d graph has made!\n",f); getchar();
		/***
		for(int i=1; i<3; i++)
		{	for(int j=1; j<=graph_num; j++)
				printf("%d", fn[i][j]);
			printf("\n");
		}
		getchar();
		/***/
	}
	fm=f;
	/***
	printf("fn is:"); getchar();
	for(int i=1; i<f; i++)
	{	for(int j=1; j<=graph_num; j++)
			printf("%d", fn[i][j]);
		printf("\n");
	}
	printf("t_fn is:"); getchar();
	for(i=1; i<f; i++)
	{	for(int j=1; j<=graph_num; j++)
			printf("%d", t_fn[i][j]);
		printf("\n");
	}
	/***/

//	printf("fm=%d",fm); getchar();
	printf("~~~time is:%f~~~~\n",((double)clock()-t)/1000);
	romove_tempfile(fn, t_fn, fm);
	printf("~~~done!~~~~\n");
//	check(f.k.txt) delete isomorphism subgraph    */
}
