import java.util.Scanner;
public class isPrimeNumber{



    public static boolean dirtyPrimeCheckMethod(int num ){

        for(int i =2 ; i<= num/2 ; i++){
            if(num % i == 0)
                return false;
            
        }
        return true;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int num = sc.nextInt();
        boolean isPrime = dirtyPrimeCheckMethod(num);
        System.out.println("Is the number " + num + " prime ? "+ isPrime);

    }
}