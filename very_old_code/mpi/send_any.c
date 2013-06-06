#include <stdio.h>
#include "mpi.h"

int main(int argc, char * argv[])
{
	int rank, size, i, buff[1];
	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	if (rank == 0)
	{
		for (i=0; i<100*(size-1); i++)
		{
			MPI_Recv(buff, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
			printf("Msg=%d from %d with tag %d\n", buff[0], status.MPI_SOURCE, status.MPI_TAG);
		}
	}
	else
	{
		for (i=0; i<100; i++)
		{
			buff[0] = rank + i;
			MPI_Send(buff, 1, MPI_INT, 0, i, MPI_COMM_WORLD);
		}
	}
	MPI_Finalize();
}
