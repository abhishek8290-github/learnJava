import java.util.Scanner;
// Print first `n` terms of Fibonacci using a loop.

public class fibonacci{


 
    public static int getFibonacci(int n ) {
        
        if(n <=2) {
            return 1 ;
        }
        return getFibonacci(n-1) + getFibonacci(n-2) ;
        
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int num = sc.nextInt();

        // This is a dirty method // better answer should be to keep this in an array and then print the array.
        for (int i = 1; i <= num; i++) {
            System.out.print(getFibonacci(i)+" ");
        }

        
    }
}