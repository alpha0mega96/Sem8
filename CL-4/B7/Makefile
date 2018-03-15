EXECS = oeSort.bin
CC = -O3
build : odd_even_sort.c
	mpicc ${CC} -o ${EXECS} odd_even_sort.c

clean : ${EXECS}
	-rm ${EXECS}
