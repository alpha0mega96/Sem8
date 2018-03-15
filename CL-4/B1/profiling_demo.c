#include<pthread.h>
#include<stdio.h>

//performing merge sort using pthreads
struct data {
	int *arr;
	int start;
	int end;
};

typedef struct data Array;

void delay(int time) {
	int ms = time*1000;
	clock_t start_time = clock();
	while(clock() < start_time + ms);
}

void merge(Array* A, int m)
{
    int i, j, k;
    int n1 = m - A->start + 1;
    int n2 = A->end - m;

    /* create temp arrays */
    int L[n1], R[n2];

    for (i = 0; i < n1; i++) L[i] = A->arr[A->start + i];
    for (j = 0; j < n2; j++) R[j] = A->arr[m + 1 + j];

    i = j = 0;
    k = A->start;

	while (i < n1 && j < n2) A->arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
	delay(1);
	while (i < n1) A->arr[k++] = L[i++];
	delay(1);
	while (j < n2) A->arr[k++] = R[j++];
}

void *merge_sort(void* d)
{
	Array* a = (Array*)d;
	if (a->start < a->end)
	{
        	// Same as (l+r)/2, but avoids overflow for large l and h
		int m = a->start+(a->end - a->start)/2;

		Array l;
		l.arr = a->arr;
		l.start = a->start;
		l.end = m;

		Array r;
		r.arr = a->arr;
		r.start = m+1;
		r.end = a->end;

		pthread_t left, right;
		pthread_create(&left, NULL, merge_sort, &l);		//merge_sort(arr, l, m);
		delay(1);
		pthread_create(&right, NULL, merge_sort, &r);		//merge_sort(arr, m+1, r);
		delay(1);

		pthread_join(left, NULL);
		pthread_join(right, NULL);
		delay(1);

		merge(a,m);
	}
}


int main() {

	Array merge_sort_data;
	int a[] = {3242,145,12323,654,123,43123,453456,31,23445,323,4576,1267,789};
	int e = sizeof(a)/sizeof(*a);

	merge_sort_data.arr = a;
	merge_sort_data.start = 0;
	merge_sort_data.end = e;

	pthread_t merging_leader;
	pthread_create(&merging_leader, NULL, merge_sort, &merge_sort_data);
	pthread_join(merging_leader,NULL);

	int i;
	printf("Sorted Array:\n");
	for (i = 0; i < e-1; i++) printf("%d, ",a[i]);
	printf("%d.\n",a[e-1]);

	return 0;
}
