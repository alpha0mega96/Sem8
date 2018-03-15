#include<stdio.h>
#include <stdlib.h>
#include<mpi.h>
int main(int argc,char ** argv)
{
	float x[15],h[15];
	int * y = NULL;
	int i,j,m,n,N,numele,rank,size,start,end,k;
	int result[15];
	//MPI_Init
	MPI_Status status;
	MPI_Init(&argc, &argv);
    	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    	MPI_Comm_size(MPI_COMM_WORLD, &size);

	if(rank == 0)
	{
		printf("enter value for m:");
		scanf("%d",&m);
		printf("enter value for n:");
		scanf("%d",&n);
		N=m+n-1;
		printf("enter the value of x(n):");
		for(i=0;i<m;i++)
			scanf("%f",&x[i]);
		printf("enter the value of h(n):");
		for(i=0;i<n;i++)
			scanf("%f",&h[i]);
		for(i=m;i<=size;i++)
			x[i]=0;
		for(i=n;i<=size;i++)
			h[i]=0;
	}
	MPI_Bcast(x,size,MPI_INT,0,MPI_COMM_WORLD);
	MPI_Bcast(h,size,MPI_INT,0,MPI_COMM_WORLD);
	
	numele=N/size;
	start=rank*numele;
	end=start+numele;
	y = (int *) malloc(sizeof(int)*(end-start));
	for(i=start,k=0;i<end;i++)
	{
		y[k]=0;
		for(j=0;j<=i;j++)
			y[k]=y[k]+(x[j]*h[i-j]);
	}
		
	if(rank == 0)
	{
		MPI_Barrier(MPI_COMM_WORLD);
		MPI_Gather(y, end-start, MPI_INT, result, size, MPI_INT, 0, MPI_COMM_WORLD );
		for(i=0;i<m+n-1;i++)
			printf("result[%d]=%d\n",i,result[i]);
	}
	MPI_Finalize();
	return 0;
}
