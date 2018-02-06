#include <stdio.h>
#include <stdlib.h>

int A, S, size_pad;
int step(int P) {
	int b = P & 1, l = P & 2;
	if (l ^ b) {	//01 or 10
		if (l)	//10
			P += S;
		else	//01
			P += A;
		P &= size_pad;
	}

	P >>= 1;
	printf("%x\n",P);
	return P;
}

int bitsize(int n) {
	int count = 0;
	while (n != 0) {
		n >>= 1;
		count++;
	}
	return count;
}
int makepad(int bits) {
	int pad = 0;
	while (bits-- != 0)
		pad = pad << 1 + 1;
	return pad;
}
int setup(int m, int r) {
	int x = bitsize( m<0 ?-m:m ), y = bitsize( r<0 ?-r:r );
	x = (x/4+1)*4;
	y = (y/4+1)*4;
	int size_pad = makepad(x + y + 1);
	A = m << (y+1);
	A &= size_pad;
	printf("%d %x\n", A, A);
	S = -m << (y+1);
	S &= size_pad;
	printf("%d %x\n", S, S);
	return y;
}

int booths(int m, int r) {
	int steps = setup (m, r);
	int P = r<<1 & 0x0F, i = 0;
	for (; i < steps; i++)
		P = step(P);
	return P>>1;
}

int main(int argc, char* argv[]) {
/*	if (argc != 3) {
		printf("Usage: %s multiplicand multiplier.\n", argv[0]);
		exit(EXIT_FAILURE);
	}
	
	char* M = argv[1], *R = argv[2];
	int m = 0, r = 0;
	if (M[0] == '-') {
		M++;
		m = atoi(M);
		m = -m;
	} else m = atoi(M);
	if (R[0] == '-') {
		R++;	
		r = atoi(R);
		r = -r;
	} else r = atoi(R);

	printf("%d, %d", m, r);*/
	int p = booths(3, -4);
	
	printf("Product is %d.\n", p);
	exit(EXIT_SUCCESS);
}
