#include <stdio.h>

int is_odd(int i)
{
	if (i%2 == 1) return 1;
	if (i%2 == 0) return 0;
}

void exchange(int * square, int * v, int * head, int j)
{
	int k=0;
printf("before....\n");
output(square, v);
	if (is_odd(*head+j))
	{
		square[8] = square[(*head+j)%8];
		square[(*head+j)%8] = square[(*head+j+1)%8];
		for (k=1; k<8; k++)
			square[(*head+j+k)%8] = square[(*head+j+k+1)%8];
		square[(*head+j)%8] = square[8];
		*head = (*head+7)%8;
	}
	else
	{
		square[8] = square[(*head+j+1)%8];
		square[(*head+j+1)%8] = square[(*head+j)%8];
		for (k=7; k>0; k--)
			square[(*head+j+k+1)%8] = square[(*head+j+k)%8];
		square[(*head+j+1)%8] = square[8];
		*head = (*head+1)%8;
	}
	square[8]= 0;
printf("after....\n");
	output(square, v);
}

void output(int * square, int * v)
{
	int i=0;
	for (i=0; i<9; i++)
	{
		printf("%d ", square[v[i]]);
		if (i%3 == 2)
		{
			printf("\n");
		}
	}
	printf("=========\n");
}

int main(void)
{

	int square[9];
	int v[9];
	int head=0, i=0, j=0;
	v[0] = 0;
	v[1] = 1;
	v[2] = 2;
	v[3] = 7;
	v[4] = 8;
	v[5] = 3;
	v[6] = 6;
	v[7] = 5;
	v[8] = 4;

	//input
	printf("input 9 numbers\n");
	for (i=0; i < 9; i++)
	{
		scanf("%d", &square[v[i]]);
	}

	output(square, v);

	if (square[0] != 0)
	{
		head = 0;
		i = 0;
		while ((square[head+i] != 0) && i<8) i++;
		if (i == 8)
		{
			printf("Input Error\n");
			return 0;
		}
		else if (is_odd(head+i))
		{
			square[head+i] = square[8];
		}
		else
		{
			square[head+i] = square[head+i+1];
			square[head+i] = square[8];
		}

		i = 0;
		while ((square[i] != 1) && i<8) i++;
		if (i == 8)
		{
			printf("abc\n");
		}
		else
		{
			head = i;
			for (i=1; i<8; i++)
				for (j=i-1; j>-1; j--)
					if (square[(head+j+1)%8] < square[(head+j)%8])
						exchange(square, v, &head, j);
		}
	}
	return 0;
}
