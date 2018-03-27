import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author compsys10
 */
public class D4 {
//Miller Rabin primality testing
    public boolean isPrime(long n, int iteration)

        {

            /** base case **/

            if (n == 0 || n == 1)

                return false;

            /** base case - 2 is prime **/

            if (n == 2)

                return true;

            /** an even number other than 2 is composite **/

            if (n % 2 == 0)

                return false;

     

            long s = n - 1;

            while (s % 2 == 0)

                s /= 2;

     

            Random rand = new Random();

            for (int i = 0; i < iteration; i++)

            {

                long r = Math.abs(rand.nextLong());            

                long a = r % (n - 1) + 1, temp = s;

                long mod = modPow(a, temp, n);

                while (temp != n - 1 && mod != 1 && mod != n - 1)

                {

                    mod = mulMod(mod, mod, n);

                    temp *= 2;

                }

                if (mod != n - 1 && temp % 2 == 0)

                    return false;

            }

            return true;        

        }

        /** Function to calculate (a ^ b) % c **/

        public long modPow(long a, long b, long c)

        {

            long res = 1;

            for (int i = 0; i < b; i++)

            {

                res *= a;

                res %= c; 

            }

            return res % c;

        }

        /** Function to calculate (a * b) % c **/

        public long mulMod(long a, long b, long mod) 

        {

            return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();

        }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        long p,q,m;
        //Choose an N-bit prime q.
        System.out.println("Enter a large prime no., q:");
        q=sc.nextLong();
        System.out.println("You entered:"+q);
        
            System.out.println("\nEnter number of iterations:");

            int k = sc.nextInt();

            //Choose an L-bit prime modulus p such that p − 1 is a multiple of q.
            System.out.println("Enter n(multiplier of q):"); 
            int n=sc.nextInt();
            m=n*q;  //n is to be multiplied by q, as p-1 is a multiple of q.
            p=m+1;  //so m is same as p-1.
            
             
            System.out.println("Value of p is:"+p);
            /** check if prime **/

           D4 d=new D4();
            boolean prime = d.isPrime(q, k);
            
            if (prime)

                System.out.println("\n"+ q +" is prime");

            else

                System.out.println("\n"+ q +" is composite");
    boolean prime1 = d.isPrime(p, k);
            
            if (prime1)

                System.out.println("\n"+ p +" is prime");

            else

                System.out.println("\n"+ p +"is composite");
                
    }
    
}

