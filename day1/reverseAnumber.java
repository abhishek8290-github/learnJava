import java.util.Scanner;

public class reverseAnumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int num = sc.nextInt();
        int reversed = 0 ;
        while(num != 0){
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
        System.out.println("The reversed number is: " + reversed);
    }
}