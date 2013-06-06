#include "mpi.h"
#include <stdio.h>

void main(int argc, char * argv[])
{
        char message[20];
        int myrank;
        MPI_Status status;
        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
        if (myrank == 0)
        {
                strcpy(message, "Hellow, process 1");
                MPI_Send(message, strlen(message), MPI_CHAR, 1, 99, MPI_COMM_WORLD);
        }
        else if (myrank == 1)
        {
                MPI_Recv(message, 20, MPI_CHAR, 0, 99, MPI_COMM_WORLD, &status);
                message[strlen(message)] = '\0';
                fprintf(stderr, "received : %s\n", message);
        }
        MPI_Finalize();
}

