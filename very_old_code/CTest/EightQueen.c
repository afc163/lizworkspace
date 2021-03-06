//八皇后问题——在8*8的棋盘上，放置8个皇后，使得任两个皇后不在同行同列同正负对角线上。
//解决方法：递归和回溯
//主要思想：8后问题转换为7后问题，依次判断合适位置。存储数组：同行记录每行皇后位置，正对角线i-j，负对角线i+j
//结果：92个结果，其中由于同构，有12个本质解
//递归的时间空间复杂度：？
//推广到n后问题，待作

int a[9],b[15],c[15],x[9];
int count=0;

void output(void)
{
	int i;
	for(i=0;i<8;i++)
		printf("%d ",x[i]);
	printf("\n");
	++count;
}

void try(int i)
{
	int  j;
	for (j=0; j<8; j++) 								//依次尝试0～7位置
		if (a[j]==1 && b[i-j+7]==1 && c[i+j]==1)	//若该行，正对角线，负对角线上都没有皇后，则放入i皇后
		{
			x[i]=j; 
			a[j] =0; 								//调整a，b，c状态
			b[i-j+7]=0;  
			c[i+j]=0; 
			if ( i<7 ) 
				try (i+1) ;  						//递增或递减
			else 
				output();						//产生一个结果，输出
		   	a[j]=1;  								//调整a，b，c状态								
			b[i-j+7]=1; 
			c[i+j]= 1;
		}  
}

main()
{
	int i;
	
	//init three array and one result array
	for (i=0; i<8; i++)
	{	
		a[i]=1;		//存储皇后所在列，若该列有皇后，则相应置为1，反之则0
		x[i]= 0;		//存放每行皇后所在的位置，随着程序的执行，在不断的变化中，之间输出结果
	}
	for (i=0; i<15; i++)
	{
		b[i]=1; 		//正对角线，i-j恒定，-7~0~7，并且b(i)+7统一到0～14
		c[i]=1;		//负对角线，i+j恒定，0～14
	}
	try(0);			//开始递归，先放一个，依次递增，反过来，从7开始递减也可
	printf("Totally have %d solutions!\n", count);
}
