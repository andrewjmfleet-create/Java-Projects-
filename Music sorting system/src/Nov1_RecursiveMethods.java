
public class Nov1_RecursiveMethods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * A recursive method is a method which calls itself
		 * with smaller input values(arguments/parameters)
		 * until we get to a terminating case(base case)
		 */
		System.out.println(returnSum(5));
		System.out.println(sumR(5));
	}
	/*
	 * Create a method to calculate the sum of the numbers
	 * from 1 to n and return the sum
	 */
	public static int returnSum(int n) {
		int sum=0;
		for(int i =1; i<=n;i++) {
			sum+=i;
		}
		return sum;
	}
	public static int sumR(int n) {
		if(n==0) {//base case
			return 0;
		}else {//recursive step 
			return n+ sumR(n-1);
			//sum of n and the previous n-1 numbers
		}
	}

	/*
	 * Use a loop to find the nth term of the sequence
	 * described below:
	 * t1=2
	 * tn=3*t(n-1)+4
	 * 
	 */
	public static int calcTerm(int n) {	
		int t = 2; 
		for (int i = 2; i <= n; i++) {
			t = 3 * t + 4;
		}
		return t;
	}
	public static int calcTermR(int n) {
		if(n==1) {
			return 2;
		}else {
			return 3*calcTermR(n-1)+4;
		}
	}
	/*
	 * Euclids algorithm:
	 * subtract smaller from larger replace larger value with the difference
	 * and repeat until the values are the same
	 */
	public static int GDC_Loop(int a, int b) {
		while (a != b) {
			if (a > b) {
				a -= b;
			} else {
				b -= a;
			}
		}
		return a; 
	}
	public static int GDC_Rec(int a, int b) {
		if (a == b) {
			return a; // Base case: when a and b are the same
		}
		if (a > b) {
			return GDC_Rec(a - b, b);
		} else {
			return GDC_Rec(a, b - a);
		}
	}
	public static long factorial1(int n) {
		if(n<0) {
			System.out.println("Not allowed");
			return 0;
		}else if(n==0||n==1) {
			return 1;
		}else {
			return n*factorial1(n-1);
		}
	}
	 public static long factorial2(int n) {
	        if (n < 0) {
	            System.out.println("Error: Factorial is not defined for negative integers.");
	            return 0;
	        }
	        long result = 1;
	        for (int i = 1; i <= n; i++) {
	            result *= i;
	        }
	        return result;
	    }
	 
}
