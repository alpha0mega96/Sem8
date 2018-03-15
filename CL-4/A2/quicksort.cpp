#include <vector>       //for vector
#include <iterator>     //for vector::interator
#include <algorithm>    //for generate and swap
#include <omp.h>        //for #pragmas
#include <ctime>        //for clock_t and clock() and CLOCKS_PER_SECONDS
#include <cstdlib>      //for exit() and EXIT_FAILURE
#include <fstream>      //for writing into a file (redirecting cout)
#include <iostream>     //for cout
using namespace std;

class sorting
{
    public:
    vector<int>::iterator partition(vector<int>, vector<int>::iterator, vector<int>::iterator);
    void quicksort(vector<int>, vector<int>::iterator, vector<int>::iterator);
};

vector<int>::iterator sorting::partition(vector<int> a, vector<int>::iterator low, vector<int>::iterator high)
{
    vector<int>::iterator i, j;
    i= low + 1;
    j= high;
    while(1)
    {
        while(i < high && *low > *i)
            i++;
        while(*low < *j)
            j--;
        if(i < j)
            iter_swap(i,j);
        else
        {
            iter_swap(low,j);
            return j;
        }
    }
}

void sorting::quicksort(vector<int> a, vector<int>::iterator low, vector<int>::iterator high)
{
    vector<int>::iterator j;

    if(low < high)
    {
        double start = omp_get_wtime();
        j = partition(a, low, high);
        double end = omp_get_wtime();
        
        cout << "Partition time for thread "<<omp_get_thread_num()<<" is: " << (end-start) << endl;
        start = omp_get_wtime();
        #pragma omp parallel sections
        {
            #pragma omp section
            {
                quicksort(a, low, j - 1);
            }

            #pragma omp section
            {
                quicksort(a, j + 1, high);
            }
        }
        end = omp_get_wtime();
        cout << "Sort time for thread "<<omp_get_thread_num()<<" is: " << (end-start) << endl;
    }
}

int main (int argc, char *argv[])
{
    sorting a;
    int i,j;
    if (argc != 2)
    {
	cerr << "Usage : " << argv[0] << " input_size"<<endl;
	exit(EXIT_FAILURE);
    }
    int size = (int)strtol(argv[1], NULL, 10);
    if (size <= 0)
        exit(EXIT_FAILURE);

    vector<int> v(size);
    for(i=0;i<size;i++)
    {
	cout<<"Enter element "<<(i+1)<<": ";
        cin>>v[i];
    }
    cout<<"Vector contents before sorting:\n";
    for(i=0;i<size;i++)
    {
        cout<<v[i]<<"\n";
    }
    cout<<"\n";

    ofstream out("out.txt");
    streambuf *coutbuf = std::cout.rdbuf(); //save old buf
    cout.rdbuf(out.rdbuf()); //redirect std::cout to out.txt!
    
    clock_t astart = clock();
	a.quicksort(v, v.begin(), v.end());
    clock_t aend = clock();
    
    cout.rdbuf(coutbuf); //reset to standard output again

    cout<<"Vector contents after sorting:\n";
    for(i=0;i<size;i++)
    {
        cout<<v[i+1]<<"\n";
    }
    cout<<"\n";

} 
