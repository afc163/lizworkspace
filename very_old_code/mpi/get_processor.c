#include <stdio.h>
#include "mpi.h"

int main(int argc, char * argv[])
{
        int myrank;
        char processor_name[20];
        int processor_len;
        int version_info;
        int version_len;

        MPI_Init(&argc, &argv);
        MPI_Comm_rank(MPI_COMM_WORLD, &myrank);

        //MPI_Get_processor_name
        MPI_Get_processor_name(processor_name, &processor_len);
        MPI_Get_version(&version_info, &version_len);

        printf("process %d on %s--%d version is %d--%d\n", myrank, processor_name, processor_len, version_info, version_len);
        fprintf(stderr, "process %d on %s--%d version is %d--%d\n", myrank, processor_name, processor_len, version_info, version_len);
        MPI_Finalize();
        return 0;
}

