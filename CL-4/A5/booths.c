#include <stdio.h>
#include <stdlib.h>

#define ABS(x) ((x < 0)? -x : x)

unsigned int A, S, size_pad;
int step(int P) {
	int b = P & 1, l = (P & 2)>>1;			// Extract 0th and 1st bit.
	if (l ^ b)					// for 01 or 10, we gotta add.
		P = (P + ((b) ? A : S)) & size_pad;	// Add/Subtract and drop carry (if any)
	printf("%x, %x %x\n", P, l, b);
	P = (P & (size_pad+1)>>1) | P >> 1;		// Custom arithmetic shift: Extract and use MSB (Sign Bit) in right shift.
	printf("%x, %u\n", P, P);
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
	int pad = 1;
	while (--bits != 0)
		pad = (pad << 1) + 1;
	return pad;
}
int setup(int m, int r) {
	int x = bitsize( m<0 ?-m:m ), y = bitsize( r<0 ?-r:r );
	x = (x/4+1)*4;
	y = (y/4+1)*4;
	int size_pad = makepad(x + y + 1);
	printf("%x\n", size_pad);
	A = m << (y+1);
	A &= size_pad;
	S = -m << (y+1);
	printf("%x\n", A);
	S &= size_pad;
	printf("%d %x\n", S, S);
	return y;
}

int booths(int m, int r) {
	if (ABS(m) > 16 || ABS(r) > 16)
		return 0;
	int steps = setup (m, r);
	int P = (r & 0x0F) << 1, i = 0;
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
