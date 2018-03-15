#include<mpi.h>
#include<stdlib.h>

extern merge_sort(int, int*);

int MPI_OddEven_Sort(int n, double *a, int root, MPI_Comm comm)
{
    int rank, size, i, sorted_result;
    double *local_a;

// get rank and size of comm
    MPI_Comm_rank(comm, &rank); //&rank = address of rank
    MPI_Comm_size(comm, &size);

    local_a = (double *) calloc(n / size, sizeof(double));


// scatter the array a to local_a
    MPI_Scatter(a, n / size, MPI_DOUBLE, local_a, n / size, MPI_DOUBLE,
        root, comm);

// sort local_a
    merge_sort(n / size, local_a);

//odd-even part
    for (i = 0; i < size; i++) {

        if ((i + rank) % 2 == 0) {  // means i and rank have same nature
            if (rank < size - 1) {
                MPI_Compare(n / size, local_a, rank, rank + 1, comm);
            }
        } else if (rank > 0) {
            MPI_Compare(n / size, local_a, rank - 1, rank, comm);
        }

        MPI_Barrier(comm);
        // test if array is sorted
        MPI_Is_Sorted(n / size, local_a, root, comm, &sorted_result);

        // is sorted gives integer 0 or 1, if 0 => array is sorted
        if (sorted_result == 0) {
            break;
        }           // check for iterations
    }

// gather local_a to a
    MPI_Gather(local_a, n / size, MPI_DOUBLE, a, n / size, MPI_DOUBLE,
           root, comm);
    return MPI_SUCCESS;
}
