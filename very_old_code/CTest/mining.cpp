#include "stdio.h"
#include "stdlib.h"
#include "string"
#include "iostream"
#include "time.h"

using namespace std;

/*************************************************************************************/
#define sup 4                  //֧�ֶ�sup

#define size 17                //ͼ��ԭʼ��ģ
#define graph_num 150          //ͼ�ĸ���
#define temp_graph_num 3500		//Ƶ����ѡͼ�ĸ���
//int size_side;         //ͼ�ı������м�����ڼ�֦������
//int size_dot;          //ͼ�ĵ������м�����ڼ�֦������


int freq[9126][4];         //����Ƶ���ȴ�txt�ж�������freq������
//int gl[size][size];        //��ȡͼ��f-1Ƶ����
//int gg[size][size];        //f-1���һ���ߺ���f��ͼ�ľ���
//int gf[size][size];        //����һ�ʱ�õľ���
int f;						//Ƶ��f�
int seq_f1;					//ͼgn��f1������
int seq_f;					//ͼgn��f������
int s_f;					//Ƶ����fʱ��ͼ�����

int seq_tf;                 //��¼����temp��ѡ��ʱ��Ǹ���

//int seq3;                  //��¼��ȥ��Ƶ�������������

char gn_sides[50][2*size];
	//ͼgn�У���f-1Ƶ�������f��ѡ��󣬼����ַ��������У����������ѯ�Ƿ����ɹ��˱�
int sides_num;		//��¼gn���Ѿ����ɵıߵ�����

//int re[size][2];

typedef struct temp_graph_freq	//����ͳ��ÿ�����һ�ߺ�,�õ������к�ѡͼ��Ƶ����
{
	char tf[size*6];
	int count;
	int ckf;	//ckf�� check flag����¼������ʱ�Ƿ�check��
}tgf;

typedef   int   (*fn_s)[graph_num+1];	//fnϵ�еľ���fn series
typedef   int   (*g_s)[size];		//gϵ�еľ���g series

/********************************************************************************/
void readfreq()                //��ÿ���ߵ�Ƶ���ȴ�txt�ж�������freq������
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

fn_s initial_fn(int (*fn)[graph_num+1])   //fn��¼��ͼƵ��n��ĸ�������ʼ��fnΪ0
{
	for	(int i=0; i<20; i++)
		for	(int j=0; j<=graph_num; j++)
		{		
			fn[i][j]=0;
		}
	return fn;
}

g_s read_graph(int gn, int (*g)[size], int filename1_type)    //����ͼ�Ĺ������󣬷������g[size][size]��
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
    strcat(filename1,filename2);    //���±��ļ���
	
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

g_s cut_unfreq_side(int scut, int (*g)[size])		//��ȥͼgn�з�Ƶ���ı�
{   
	int i=0, j=0;
	for(i=0; i<size; i++)	//��������һ����Ҫ��ȥ���������for�Ͳ����ˣ���������ֱ�ӽ�������Ϊ��
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

g_s cut_isolated_dot(int dcut,int (*g)[size])		//��ȥ�����㣨����������û�б�������
{
	int i=0, j=0;
	for(j=0; j<size; j++)	//��������һ����Ҫ��ȥ���������for�Ͳ����ˣ���������ֱ�ӽ�������Ϊ��
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

g_s cut_unfreq(int (*g)[size])         //ͨ������Ƶ���ȱ���ȥ����ͼ�з�Ƶ���ı�
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
	
	for(j=2; j<size && g[0][j]!=0; j++)	//����ĳһ��j
	{
		for(i=2; i<size; i++)	//���ҵ���ĳһ�˵�
			if(g[i][j]==1)
			{
				p=i;
				break;
			}
		for(i=size-1; i>=2; i--)	//���ҵ�����һ���˵�
		    if(g[i][j]==1)
			{
				q=i;
				break;
			}
		temp=freq[((g[0][j]-65)*351)+jcalculate(g[p][0]-97)+(g[q][0]-g[p][0])][3];
//		printf("%d ",temp);
//		/*****/printf("%c%c%c freq is:%d\n",g[0][j],g[p][0],g[q][0],temp);
//		getchar();
        if(temp<sup)	//��ѯ��Ƶ���ȱ��õ������ߵ�Ƶ���ȣ����С��֧�ֶȣ����ȥ�˱�
		{
			g = cut_unfreq_side(j,g);		//��ȥͼgn�в�Ƶ���ı�j
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
	for(i=2; i<size; i++)		//����ĳһ��i
	{
		flag=0;		//����û�б���˵�����
		for(j=2; j<size; j++)
			if(g[i][j]==1)
			{
				flag=1;
				break;
			}
		if(flag==0)		//�������ң���û�б���˵���������˵�Ϊ�����㣬ȥ���˵�
		{
            g=cut_isolated_dot(i,g);		//��ȥ�����㣨����������û�б�������
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

void print_graph(int gn, int (*g)[size], int filename1_type)     //������˺��ͼ�Ĺ�������g[size][size]����ͼ��n.txt��
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

void prune()                  //��ԭͼ��ͼ��.txt����ȥ��Ƶ���ߣ��õ���ͼ��n.txt��
{
	int gn; 
	gn=1;
	while(gn <= graph_num)
	{
		int og[size][size];         //��δ��֦��ͼ�ӡ�ͼ��.txt���ж����������g[size][size]��
		int (*g)[size]=og;
		g=read_graph(gn,g,1);
		/********/
//		getchar();
//		printf("gn before cut %d\n",gn);
		/*******/
		g=cut_unfreq(g);              //������ͼ�ķ�Ƶ����ȥ��
//		printf("gn after cut %d\n",gn);
		print_graph(gn,g,1);              //������˺��ͼ�Ĺ�������g[size][size]����ͼ��n.txt��
		gn++;
	}
}

int get_gn_sides_num(int (*g)[size])		//��ȡgn�ı���   
{
	int gn_f1_count=0,j;
    for(j=2;j<size;j++)
	{
		if(g[0][j]!=0)
			gn_f1_count++;
   	}
	return gn_f1_count;
}

g_s gn_side_to_array(int (*g_f1)[size], int (*g)[size])     //��gn�ĵ�seq_f1����ת����ͼ����g_f1�д��
{
	int i,j,p,q;
	int side = seq_f1 + 1;
	for(i=0;i<size;i++)        //�Ƚ�g_f1��ʼ��Ϊ0
		for(j=0;j<size;j++)
		{
			g_f1[i][j]=0;
		}
    for(i=2;i<size;i++)		//�ҵ������ߵ�һ���˵�
		if(g[i][side]==1)
		{
			p=i;
			break;
		}
	for(i=size-1;i>=2;i--)	//�ҵ������ߵ���һ���˵�
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

fn_s print_freq_one(int (*fn)[graph_num+1])    //�����ͼ��Ƶ��һ�����ͼ��.f1.�ڼ���.txt��
{
	int gn=1;
	while( gn<=graph_num )         //��ͼһ��ʼ�������ͼ��Ƶ��һ�
	{
		int og[size][size];
		int (*g)[size]=og;
        g=read_graph(gn,g,2);       //���롰ͼ��n.txt��������g[size][size]
		int gn_f1_count = get_gn_sides_num(g);     //��ȡgn�ı���
		fn[1][gn] = gn_f1_count;		//��gn�ı��������ͳ������
		seq_f1 = 1;				 //��ǵڼ�������ͼ��.f1.�ڼ���.txt����
        while ( gn_f1_count != 0 )
		{
			int og_f1[size][size];
			int (*g_f1)[size]=og_f1;
			g_f1 = gn_side_to_array(g_f1,g);	//��gn�ĵ�seq_f1����ת����ͼ����g_f1�д��
			print_graph(gn, g_f1, 2);    //���ͼgn�ĵ�seq_f1��Ƶ��һ����ߣ�
			gn_f1_count--;
			seq_f1++;
		}
		gn++;
	}
	return fn;
}

int get_last_f_num(int (*fn)[graph_num+1], int last_f)             //����f��Ƶ���������
{
	for(int j=1;j<=graph_num;j++)
		if(fn[last_f][j]!=0)
			return 1;
	return 0;	
}

g_s modify_g(int (*g_last_f)[size], int (*g)[size], int (*g_temp)[size])
//��g_lasf_f�����еı��ڲ��վ���g��ɾ��
{
	int gl_firstj;
	int i;
	int j;
	int n;	//m,n�ֱ�����g���������

	for (i=0; i<size; i++)		//��ʼ��temp��Ϊ0
		for (j=0;j<size; j++)
		{
			g_temp[i][j] = 0;
		}

	for(gl_firstj=2; gl_firstj<size; gl_firstj++)	//�ҵ�g_lst_f�ĵ�һ������g�е�λ��
	{
		if( g[0][gl_firstj] == g_last_f[0][2] )
			break;
	}
	
	for(i=2; i<size; i++)		//��������ĵ㼯����g_temp��
	{
		g_temp[i][0] = g[i][0];
		g_temp[i][1] = g[i][1];

	}
	
	int temp_flag=2;		//��g_last_f��û�г��ֵı���temp���д��λ�ô�2��ʼ
	for(j=gl_firstj; j<size; j++)
	{
		int flag = 0;		//��ʼ��Ϊg�������߲���g_last_f��
		for(n=2; n<size; n++)
			if(g[0][j] == g_last_f[0][n] && g[1][j] == g_last_f[1][n])
			{
				flag = 1;
				break;
			}
		if(flag==0)		//���������ѯ����������߲���g_last_f�У�����Է���temp����
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
	int i,k,flag=0;		//flag��ʼΪ0��ʾ����g_f��û������˵�
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
	for(i=2; i<size; i++)		//Ѱ�Ҷ˵�ĺ���λ��
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
	if(g_f[i][0] == g[r][0])	//���g_f���д˱�ŵĵ�
	{
		k=i;
		do
		{
			if(g_f[k][0]==g[r][0] && g_f[k][1]==g[r][1])	//�鿴�Ƿ�����ͬ�±�ĵ�
			{
				flag=1;
				break;
			}
			k++;
		}while(g_f[k][0] == g_f[k-1][0]);
	}
    if(flag==0)
		return i;		//���û�д���ͬ�±�Ĵ˵㣬����g_f�в���˶˵�
	else
		return -k;		//����Ѿ��д���ͬ�±�Ĵ˵㣬������g_f�ĵ�߽��Ӵ���Ϊ1
}

g_s gl_to_gf(int n, int (*g)[size], int (*g_last_f)[size], int (*g_f)[size])
//ͼg_last_f������ձ�ĵ�n�бߣ�������������ͼ����g_f��
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
	for(i=0;i<size;i++)		//�Ƚ�g_f��ʼ���ú�g_last_fһ��
		for(j=0;j<size;j++)
		{
			g_f[i][j] = g_last_f[i][j];
		}
	for(m=2;m<size;m++)		//�ҵ�Ҫ�ӵ������ߵ�һ���˵㣬����p��
		if(g[m][n]==1)
		{
			p=m;
			break;
		}
	for(m=size-1;m>=2;m--)		//�ҵ�Ҫ�ӵ������ߵ���һ���˵㣬����q��
		if(g[m][n]==1)
		{
			q=m;
			break;
		}
    for(j=2; j<size; j++)		//Ѱ��������Ҫ��g_f�в����λ��
	{
		if( g_f[0][j]>=g[0][n] || g_f[0][j]==0 )
			break;
	}
	
	int temp = j;		//�˱�Ҫ����g_f�ĵ�temp��
	for(j=size-2; j>=temp; j--)		//��temp��һ�п�ʼͨͨ�����ƶ�һ�У��ó�temp��һ��
		for(i=0;i<size;i++)
		{
			g_f[i][j+1]=g_f[i][j];
		}
	for(i=0; i<size; i++)	//�Ƚ�g_f��һ����0һ�£���ֹ��ǰ��һ����1�������渳1�Ļ���һ��
		g_f[i][temp] = 0;
	g_f[0][temp] = g[0][n];		//��temp�в���ߵı��
	g_f[1][temp] = g[1][n];
	//�����Ѿ����ߺŲ���g_f,����Ҫ����ı�Ų���g_f
//	/***/printf("start dot position:"); getchar();
	i = dot_position(p,g,g_f);		//Ѱ������һ����Ĳ���λ��
	if(i<0)
	{
		g_f[-i][temp]=1;		//����Ѿ��д���ͬ�±�Ĵ˵㣬������g_f�ĵ�߽��Ӵ���Ϊ1
	}
	else			//���û�д���ͬ�±�Ĵ˵㣬����g_f�в���˶˵�
	{
		for(int k=size-2;k>=i;k--)
		{
			for(j=0;j<size;j++)
			{
				g_f[k+1][j]=g_f[k][j];
			}
		}
		for(j=0;j<size;j++)	//��ǰû��g[p][0]g[p][1]��һ��
			g_f[i][j]=0;
		g_f[i][0]=g[p][0];
		g_f[i][1]=g[p][1];
		g_f[i][temp]=1;
	}
	
	i=dot_position(q,g,g_f);		//Ѱ����һ���˵�Ĳ���λ��
	if(i<0)
	{
		g_f[-i][temp]=1;		//����Ѿ��д���ͬ�±�Ĵ˵㣬������g_f�ĵ�߽��Ӵ���Ϊ1
	}
	else		//���û�д���ͬ�±�Ĵ˵㣬����g_f�в���˶˵�
	{
		for(k=size-2;k>=i;k--)
		{
			for(j=0;j<size;j++)
			{
				g_f[k+1][j]=g_f[k][j];
			}
		}
		for(j=0;j<size;j++)	//��ǰû��g[q][0]g[q][1]��һ��
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

int compik(int i, int k, int (*g_f)[size])		//�Ƚϵ�i����k�еĵ�һ���ߵ�����
{
	int ci,ck;		//c��count�����ڼ�¼i�к�k�е�ͬһ��ߵ�����
	int j=2;
	int r;
	while(g_f[0][j]!=0 && j<size)
	{
		ci=0;
		ck=0;
		r=j;	//�ȴӵ�һ���߿�ʼ
		do
		{
			if(g_f[i][r]==1)
				ci++;
			if(g_f[k][r]==1)
				ck++;
			r++;
		}while(g_f[0][r]==g_f[0][j]);	
		if(ci!=ck)		//����Ƚϳ����������٣���ֹͣ�Ƚ�
			break;
		j=r;		//����ʹ��¸����ı߿�ʼ�Ƚ�
	}
	if(ci==ck)
		return 0;        //i����k�и��ֱ�����һ����
	if(ci>ck)
		return -1;      //i�б�k���ڵ�һ������ͬ�ߵ������϶�
	if(ci<ck)
		return 1;        //i�б�k���ڵ�һ������ͬ�ߵ���������
}

int compikc(int i, int k, int (*g_f)[size])	//�Ƚϵ�i����k�еĴ�������ɵĶ�����ֵ�Ĵ�С
{
	int j=2;	//�ӵ�һ���߿�ʼ
	while( g_f[0][j]!=0 && j<size )
	{
		if( g_f[i][j] < g_f[k][j] )
			return 1;
		if( g_f[i][j] > g_f[k][j] )
			return 0;
		j++;
	}
}	

int compjk(int j, int k,  int (*g_f)[size])		//�Ƚϵ�j����k�е�����������ɵĶ�����ֵ�Ĵ�С
{
	int i=2;	//�ӵ�һ���㿪ʼ
	while( g_f[i][0]!=0 && i<size)
	{
		if( g_f[i][j] < g_f[i][k] )		//���k������������ɵĶ�����ֵ��j�еĴ�
		{
			return 1;
		}
		if( g_f[i][j] > g_f[i][k] )		//���k������������ɵĶ�����ֵ��j�е�С
		{
			return 0;
		}

		i++;
	}
}

g_s standard_before(int (*g_f)[size])
//��g_f��ԭΪԭͼ�еĵ�ͱߵ�˳��Ϊ�����ܹ��ڱ�׼����õ��ľ����Ψһ��
{	
	int i,k,j,temp;

	for(i=2; g_f[i][0]!=0 && i<size; i++)	//�����л�ԭ����ͬ����У����С�ķ�ǰ��
		for(k=i+1; g_f[k][0]==g_f[i][0] && k<size; k++)
		{
			if( g_f[k][1] < g_f[i][1] )	//ͬһ���У����k�е���ű�i�е�С�������k�к�i��
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
		}

	for(j=2; g_f[0][j]!=0 && j<size; j++)	//�����л�ԭ����ͬ����У����С�ķ�ǰ��
		for(k=j+1; g_f[0][k]==g_f[0][j] && k<size; k++)
		{
            if( g_f[1][k] < g_f[1][j] )	//ͬһ���У����k�е���ű�i�е�С�������k�к�i��
				for(i=0; i<size; i++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[i][k];
					g_f[i][k] = temp;
				}
		}

	return g_f;
}

g_s standard_gf(int (*g_f)[size])       //���˱ߺ�õ���g_f����g_f��׼��
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
	for(i=2; g_f[i][0]!=0 && i<size; i++) //���б任�Ƚ�,��ͬһ����У�����һ����������ͬ��������ĵ��ǰ��
		for(k=i+1; g_f[k][0]==g_f[i][0] && k<size; k++)	
		//���ڵ�i�У���������һ�п�ʼ����������ͬ����Ƚϵ�һ���ߵ�����
		{
			flag=0;		//�����i�еĵ�һ���ߵ�������k�еĶ�
			flag=compik(i,k,g_f);		//���ñȽϺ������Ƚϵ�i����k�еĵ�һ���ߵ�����
			if(flag==1)		//���k�еĵ�һ������ͬ�ߵ�������i�࣬�򻥻�i�к�k��
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
		}

    /***
	printf("after �б任, g_f is:\n"); getchar();
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
			if(flag==0)	//������еı�����ͬ����Ƚ�����ɵĶ��������Ĵ�С�������ǰ��
			{
				flagc=compikc(i,k,g_f);	//flagc �� ��¼��һ�е�������ɶ����ƴ�
				if(flagc==1)		//���k�е���ɵĶ����Ʊ�i�д��򻥻�i�к�k��
				for(j=0;j<size;j++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[k][j];
					g_f[k][j] = temp;
				}
			}
		}

	for(j=2; g_f[0][j]!=0 && j<size; j++)	//���б任�Ƚϣ�ͬһ����У�������������ɵĶ�����ֵ��ı߷�ǰ��
		for(k=j+1; g_f[0][k]==g_f[0][j] && k<size; k++)
		//���ڵ�j�У���������һ�п�ʼ����������ͬ����Ƚ�����������ɵĶ�����ֵ
		{
			flag=0;		//�����i�е�����������ɵĶ�����ֵ��k�еĴ�
			flag=compjk(j,k,g_f);		//���ñȽϺ������Ƚϵ�j����k�е�����������ɵĶ�����ֵ�Ĵ�С
            if(flag==1)		//���k������������ɵĶ�����ֵ��j�еĴ��򻥻�j�к�k��
				for(i=0; i<size; i++)
				{
					temp = g_f[i][j];
					g_f[i][j] = g_f[i][k];
					g_f[i][k] = temp;
				}
		}

	/***
	printf("after �б任, g_f is:\n"); getchar();
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
	for(i=0; g_f[0][j]!=0 && j<size; i=i+2)		//��gn�д˴����ɵĺ�ѡͼg_f��Ϊ�ַ���gf
	{
		gf[i] = g_f[0][j];
		gf[i+1] = g_f[1][j]+48;
		j++;
	}
	gf[i] = '\0';
	for(i=0; i<sides_num; i++)		//��֮ǰ�Ĵ������бȽϣ������ǰ�й��򷵻�1�����򷵻�0
		if( strcmp(gn_sides[i], gf) == 0 )
			return 1;
	return 0;
}

void add_gf(int (*g_f)[size])
{
	int i=0, j=2;
	for(i=0; g_f[0][j]!=0 && j<size; i=i+2)		//��gn�д˴����ɵĺ�ѡͼg_f��Ϊ�ַ�������gn_sides����
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
	int (*g_f)[size]=og_f;	//���������ԭͼg_last_f���һ��Ƶ���ߺ��ͼ����ѡ����
	int og_temp[size][size];		
	int (*g_temp)[size]=og_temp;	//����һ��temp���󣬴����g_last_f��û�еı�
	int i;
	int j;
	int m;
	int n;

//	/***/printf("before modify"); getchar();

	g_temp = modify_g(g_last_f, g, g_temp);		//��g_lasf_f�����еı��ڲ��վ���g��ɾ��
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

	for(i=2; i<size; i++)		//��ȡ��g_last_f�е�һ����
		for(m=2; m<size; m++)			//��g���н��в�ѯ,�Ƿ�ӵ�к��д˵�ı�
			if(g_last_f[i][0]==g[m][0] && g_last_f[i][1]==g[m][1])		//����g���ҵ��˵�
				for(n=2; n<size; n++)
					if(g[m][n]==1)
					{
//						/***/printf("before gl to gf"); getchar();
						g_f = gl_to_gf(n, g, g_last_f, g_f);	//�����ǰû�мӹ��������˱�
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
						g_f = standard_gf(g_f);		//���˱ߺ�õ���g_f����g_f��׼��
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
						flag = check_gf(g_f);	//��ѯgn_sides������֪�����gn���Ƿ��Ѿ�����
//						printf("before print, flag=%d",flag); getchar();
						if(flag == 0)	//���û�й��������gn_sides�������������ԡ�ͼ��.tf?.�ڼ���.txt����ʽ��ӡ����
						{
							add_gf(g_f);		//���˱߼���gn��sides��
							seq_tf++;
							print_graph(gn, g_f, 3);     //�����seq_tf���gg�������Ϊ����ͼ��.tf?.�ڼ���.txt��							
						}
//						/***/printf("after print,i=%d,m=%d,n=%d",i,m,n); getchar();
					}
//	/***/printf("opened 00%d.f%d.0%d",gn,f-1,seq_f); getchar();
//    return;
}

fn_s generate_gn_f(int gn, int (*fn)[graph_num+1], int (*t_fn)[graph_num+1])
//��gn��f-1Ƶ���������gn��f��ѡ�
{
//	gnf=fn[f-1][gn];      //gnf����ͼgn�ж��ٸ�Ƶ����
	int og[size][size];
	int (*g)[size]=og;
	g = read_graph(gn,g,2);
	//���롰ͼ��n.txt��������g[size][size],����g_last_f[size][size]���Բ�ѯg[size][size]���Ӷ����й���������ӱ�

	seq_tf=0;		//����ÿ��ͼ�������¼��ѡ�������ı�����ʼ��Ϊ0

	sides_num = 0;		//׼����f-1�����f��ѡ�����ߴ�����ʼΪ0��

	for( seq_f=1; seq_f <= fn[f-1][gn]; seq_f++)
	{
		int og_last_f[size][size];
		int (*g_last_f)[size]=og_last_f;
		g_last_f = read_graph(gn, g_last_f,3);		//����ͼgn�ĵ�seq_f��f-1Ƶ�������g_last_f������
		add_edge(gn,  g_last_f, g);		
		//��ͼgn��f-1Ƶ������һ���ߣ���ѯgn_sides����δ����������Ϊf��ѡ������ԡ�ͼ��.tf?.�ڼ���.txt����ʽ���
	}
	t_fn[f][gn] = seq_tf;
	return t_fn;
}

tgf *initial_gc(tgf gc[temp_graph_num])	//��ʼ���ṹ������gc������ѡ����ִ�����ʼ��Ϊ0
{
	for(int i=0; i<temp_graph_num; i++)
	{
		gc[i].count=0;
		gc[i].ckf=0;	//��ʼΪ0����ʾû�б�check��
	}
	return gc;
}

char *string_tf(int gn, char gntf[6*size])
//����ͼgn��seq_tf����ѡ��������g_f��������ѹ�����ַ�������gntf��
{
	int og_f[size][size];
	int (*g_f)[size]=og_f;
//	/***/printf("f=%d	gn=%d	seq_tf=%d",f,gn,seq_tf);	getchar();
	g_f = read_graph(gn, g_f, 4);
	int gntfi;	//gntf���±�
	gntfi = 0;
	int i,j,p,q;
	gntf[gntfi]=gn+96;
	//��ͼ���д���ַ���ͷ����ֹ������ͳ��ʱ������ͬһgn�У�ת��Ϊ�ַ�����ͬ���ͼ���ͳ�Ƶ����
	gntfi++;
	for(j=2; g_f[0][j]!=0 && j<size; j++)
	{
		for(i=2;i<size;i++)		//�ҵ������ߵ�һ���˵�
		if(g_f[i][j]==1)
		{
			p=i;
			break;
		}
		for(i=size-1;i>=2;i--)	//�ҵ������ߵ���һ���˵�
		if(g_f[i][j]==1)
		{
			q=i;
			break;
		}
		gntf[gntfi] = g_f[0][j];	//�ȷ���ߵı��
		gntf[gntfi+1] = g_f[p][0];	//�ٷ���һ���˵�
		gntf[gntfi+2] = p + 96;	//����˵��λ�ã���λ��ת��ΪСд��ĸ����Ϊλ�ÿ����м�ʮ��һ�����ֲ��ܱ�ʾ��
		gntf[gntfi+3] = g_f[q][0];	//������һ���˵�
		gntf[gntfi+4] = q + 96;	//������һ�����λ��
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
//����ַ���gntf�Ƿ���gc���м�¼������У��򷵻����ڵ�Ԫ�ţ����û�У�����-1
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
		//�������ͬͼ�е�������ѡfͼ�����ַ�����ͬ���򷵻�-2��ͳ�Ʒ��治���κβ���
/*	tgf tgc[temp_graph_num];
	char tgntf[6*size];
	for(i=0; i<gci; i++)	//��ͳ�ƿ��У���ÿ���ַ����ĵ�һ��ȥ��������gnĨȥ
	{
		for(j=1; gc[i].tf[j]!='\0'; j++)
			tgc[i].tf[j-1]=gc[i].tf[j];
		tgc[i].tf[j-1]='\0';
	}
	for(j=1; gntf[j]!='\0'; j++)
		tgntf[j-1]=gntf[j];
	tgntf[j-1]='\0';	//��ƥ�Դ��У����ַ����ĵ�һ��ȥ��������gnĨȥ
	for(i=0; i<gci; i++)	//�ٴ����Ƚϲ�������Ϊ��ǰ�Ѿ���̭��ͬͼͬ������������Դ˴�����ͼͬ�������
		if( strcmp(tgc[i].tf, tgntf) == 0 )
			return i;
*/	
	return -1;	//�ַ���gntf�ڿ���û�г��ֹ�����Ҫ�������
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

	for(i=0; i<gci; i++)	//�ӵ�һ������ʼɨ��
	{
		if(gc[i].ckf==1)	//����˴��Ѿ�����¼��gcn����������һ����
			continue;
		for(j=1; gc[i].tf[j]!='\0'; j++)	//ȡ��gcn�еĵ�i�еĴ�������ȥgn��Ĵ�����gc��
			gcn[gcni].tf[j-1]=gc[i].tf[j];
		gcn[gcni].tf[j-1]='\0';
		gcn[gcni].count=1;
		for(p=i+1; p<gci; p++)
		{
			for(j=1; gc[p].tf[j]!='\0'; j++)	//ȡ��gcn�еĵ�p�еĴ�������ȥgn��Ĵ�����tft��
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
	tgntf[j-1]='\0';	//��ƥ�Դ��У����ַ����ĵ�һ��ȥ��������gnĨȥ
	for(i=0; i<gci; i++)
		if( strcmp(gc[i].tf, tgntf) == 0 )
			break;
	return gc[i].count;
}

fn_s choosefreq(int (*fn)[graph_num+1], int (*t_fn)[graph_num+1])
//�Ӻ�ѡf���ѡ��Ƶ���ģ��ԡ�ͼ��.f?.�ڼ���.txt����ʽ��ӡ������ͬʱ����ͼƵ�����������fn
{	
	int gci;
	gci = 0;	//gc����±꣬��ʼΪ0
	int gn;
	int i,j;
	int n;	//m,nΪgר�õģ���ע�к���
	int position;
	tgf ogc[temp_graph_num];
	tgf *gc=ogc;	//gc������¼������ͼͬ��δ�ϲ���ͼͳ�Ʊ�
	tgf ogcn[temp_graph_num];
	tgf *gcn=ogcn;	//gcn��ʾgc new,������������gc������gcn�У�Ϊ��ʽ��ͼͳ�Ʊ�

	gc = initial_gc(gc);	//gc�ṹ�������������ĳһ��ѡ��ѹ���ɵ��ַ�����ֻȥ����ͬͼͬ��������
	gcn = initial_gc(gcn);

	for(gn=1; gn<=graph_num; gn++)	//�Ƚ����ɺ�ѡf����м���Ƶ���Ⱦ��󣬴�ͼһ��ʼ
		for(seq_tf=1; seq_tf<=t_fn[f][gn]; seq_tf++)
		{
			int flag = -1;	//����gn�еĵ�seq_tf����ѡ����gc����û�г��ֹ�
			char ogntf[6*size];
			char *gntf=ogntf;	//���gn�еĵ�seq_tf����ѡ��ѹ���ɵ��ַ���
			gntf = string_tf(gn, gntf);	//�ǵ�����'\0'
//			/***/getchar();
			flag = check_gn_tempgraph(gc, gci, gntf);	//flag����-1������gc�г���ti���л���-2
			/***
			printf("after check, gntf is:\n");
			for(i=0; gntf[i]!='\0'; i++)
				printf("%c",gntf[i]);
			printf("\n");
			getchar();
			/***/
			if(flag == -1)
			{
				gc = add_gn_tempgraph(gc, gci, gntf);//�����ǰû�г��ֹ��������gc��ĵ�gci����Ԫ
				gci++;
			}
//			if(flag >= 0)
//				gc = add_count(gc, gci, flag);		//��������ǰ���ֹ��ĵ�Ԫ����countֵ��1
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

	gcn = join_common_string(gc, gcn, gci);	//��gc��������ȥ����ͼͬ�������
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

	for(gn=1; gn<=graph_num; gn++)	//����ɨ���ѡf�����Ƶ�����ԡ�ͼ��.f?.�ڼ���.txt����ʽ��ӡ��������ͼһ��ʼ
	{
		seq_f = 0;
		for(seq_tf=1; seq_tf<=t_fn[f][gn]; seq_tf++)
		{
			int tf_sup;
			char ogntf[6*size];	
			char *gntf=ogntf;	//���gn�еĵ�seq_tf����ѡ��ѹ���ɵ��ַ���
			gntf = string_tf(gn, gntf);	//�ǵ�����'\0'
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
	s_f=0;	//�����s_f��ӡ��Ƶ����ΪfʱƵ���
	for(i=0; i<gci; i++)
	{
		int g[size][size]={0};
		if(gc[i].count>=sup)
		{
			n=2;
			for(j=0; gc[i].tf[j]!='\0'; j=j+5)	//���ַ�����ԭΪ
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
		remove_graph(gn, 1);	//ɾ����ͼ��n.txt��ϵ��
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
    readfreq();                   //��ÿ���ߵ�Ƶ���ȴ�txt�ж�������freq������
	
	int fm;	//��¼������������ѡ��fm-1����ļ�

	int ofn[20][graph_num+1];
	int (*fn)[graph_num+1] = ofn;      //fn��¼��ͼƵ��n��ĸ���

	int ot_fn[20][graph_num+1];		//temp_fn��¼��ѡn��ĸ���
	int (*t_fn)[graph_num+1] = ot_fn;

	double t;

	fn = initial_fn(fn);            //fn��¼��ͼƵ��n��ĸ�������ʼ��fnΪ0
	t_fn = initial_fn(t_fn);

	prune();                      //��ԭͼ��ͼ��.txt����ȥ��Ƶ���ߣ��õ���ͼ��n.txt��
	
	t=(double)clock();
	
	fn = print_freq_one(fn);         //�����ͼ��Ƶ��һ�����ͼ��.f1.�ڼ���.txt��
	
	int gn;
	f = 2;                           //������Ƶ��������ߣ���ʼ
	int last_f_num = get_last_f_num(fn,f-1);	//���������Ƶ��һ���
//	/***/printf("frequent 1 has been made\n"); getchar();
	while(last_f_num==1)			//���last_f_num==0,������ϴ���Ƶ������ɣ������
	{
		for(gn=1; gn<=graph_num; gn++)	//����Ƶ��f�ʱ����ͼһ��ʼ
			if(fn[f-1][gn]!=0)			//���ͼgnû��Ƶ��f-1�����Ͳ�Ҫ��ͼgn�������f�
			{
				t_fn = generate_gn_f(gn, fn, t_fn);		//��gn��f-1Ƶ���������gn��f��ѡ�
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
		//�Ӻ�ѡ���ͼ��.tf?.�ڼ���.txt������ѡ��Ƶ������õ���ͼ��.f?.�ڼ���.txt��
		//ͬʱҪ��seq3����fn[f][gn]��
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
