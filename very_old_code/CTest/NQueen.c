//N皇后问题——在N*N的棋盘上，放置N个皇后，使得任两个皇后不在同行同列同正负对角线上。
//解决方法：递归和回溯
//主要思想：N后问题转换为N-1后问题，依次判断合适位置。存储数组：同行记录每行皇后位置，正对角线i-j，负对角线i+j
//结果：//92个结果，其中由于同构，有12个本质解
//递归的时间空间复杂度：？

#define N  20                                   //N个皇后
#define M  2*N -1                              //正负对角线的数目
int a[N],b[M],c[M],x[N];
int count=0;

void output(void)
{
	int i;
	for(i=0;i<N;i++)
		printf("%d ",x[i]);
	printf("\n");
	++count;
}

void try(int i)
{
	int  j;
	for (j=0; j<N; j++) 							//依次尝试0～N-1位置
		if (a[j]==1 && b[i-j+N-1]==1 && c[i+j]==1)	//若该行，正对角线，负对角线上都没有皇后，则放入i皇后
		{
			x[i]=j; 
			a[j] =0; 								//调整a，b，c状态
			b[i-j+N-1]=0;  
			c[i+j]=0; 
			if ( i<N-1 ) 
				try (i+1) ;  						//递增或递减
			else 
				output();						    //产生一个结果，输出
		   	a[j]=1;  								//调整a，b，c状态								
			b[i-j+N-1]=1; 
			c[i+j]= 1;
		}  
}

main()
{
	int i;
	
	//init three array and one result array
	for (i=0; i<N; i++)
	{	
		a[i]=1;		    //存储皇后所在列，若该列有皇后，则相应置为1，反之则0
		x[i]= 0;		//存放每行皇后所在的位置，随着程序的执行，在不断的变化中，之间输出结果
	}
	for (i=0; i<M; i++)
	{
		b[i]=1; 		//正对角线，i-j恒定，-(N-1)~0~N-1，并且b(i)+N-1统一到0～M-1
		c[i]=1;		    //负对角线，i+j恒定，0～M-1
	}
	try(0);			    //开始递归，先放一个，依次递增，反过来，从N-1开始递减也可
	printf("Totally have %d solutions!\n", count);
}
