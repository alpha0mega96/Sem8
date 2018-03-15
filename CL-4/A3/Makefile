EXECS = A3.bin
OPTS = -n 4

build : A3.c
	mpicc -o ${EXECS} A3.c

run : build
	mpiexec ${OPTS} ./${EXECS}

test : testing_%.c
	mpicc -o testing_%.bin $<
testrun : testing_%.bin
	mpiexec ${OPTS} $<

clean : ${EXECS} testing_%.bin
	-rm ${EXECS} out.txt testing_%.bin
