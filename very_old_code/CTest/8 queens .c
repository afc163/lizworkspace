int a[9],b[15],c[15],x[9];
int count;
count=0;

output()
{
	int i;
	for(i=1;i<9;i++)
	printf("%d ",x[i]);
	printf("\n");
	++count;
}

try(int i)
   {
     int  j;
     for (j=1; j<=8; j++) 
		if (a[j]==1 && b[i-j+7]==1 && c[i+j-2]==1)
		{  x[i]=j; a[j] =0; b[i-j+7]=0;  c[i+j-2]=0; 
		    if ( i<8 ) try (i+1) ;  else output();
		   a[j]=1; b[i-j+7]=1; c[i+j-2]= 1;
	    }  
   }

main()
{
	int i;
	for(i=0;i<9;i++)
	{a[i]=1; x[i]=1;}
	for(i=0;i<15;i++)
	{b[i]=1; c[i]=1;}
	try(1);
	printf("Totally have %d solutions!",count);
}
