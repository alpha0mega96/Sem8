EXECS = A3.bin
OPTS = -n 4

build : A3.c
	mpicc -o ${EXECS} A3.c

run : build
	mpiexec ${OPTS} ./${EXECS}

clean : ${EXECS}
	-rm ${EXECS}

test : testing_%.c
	mpicc -o testing_%.bin $<

testrun : testing_%.bin
	mpiexec ${OPTS} $<

testclean : testing_%.bin
	-rm out.txt testing_%.bin


