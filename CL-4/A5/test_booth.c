tatic unsigned int ashr(unsigned int P, int n, int s)
{
	unsigned int t;
	int highbit = 1<<(s-1);

	t = P >>= n;
	
	/* Is high bit set in P in s bit data type */
	if(P & highbit)
		t |= highbit;
		
	return t;
}

unsigned int A = 0x0060, S = 0x01a0;

/* Perform step of Booth's Algorithm */
static unsigned int step(unsigned int P, int size)
{
	switch(p & 3)
	{
		/* P ends in 00 or 11, do nothing */
		case 0:
		case 3:
		break;
		
		/* P ends in 01, add A */
		case 1:
			P = P + A;
		break;
		
		/* P ends in 10, add S */
		case 2:
			P = P + S;
		break;
	}
	
	ashr(P, 1, size);
}

int main() {
	int size = 0x01ff;
	
