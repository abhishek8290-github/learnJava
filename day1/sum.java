import java.util.Scanner;

public class sum {

    public static int sum(int num1, int num2) {
        int sum = num1 + num2;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("Sum of Two Numbers");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the first Number: ");
        int num1 = sc.nextInt();
        System.out.print("Enter the Second Number: ");
        int num2 = sc.nextInt();
        
        System.out.printf("Formatted: %d\n", sum(num1, num2));
    }
}