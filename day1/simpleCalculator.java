import java.util.Scanner;

public class simpleCalculator {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number : ") ;
        int num1 = sc.nextInt() ;

        System.out.print("Enter the operator : ") ;
        String op = sc.next() ;




        System.out.print("Enter a number : ") ;
        int num2 = sc.nextInt() ;


        switch(op){
            case "+" :
                System.out.println(num1 + num2);
                break;
            case "-" :
                System.out.println(num1 - num2);
                break;
            case "*" :
                System.out.println(num1 * num2);
                break;
            case "/" :
                if (num2==0){
                    System.out.println("Division by zero is not possible");
                    break;
                }
                System.out.println(num1 / num2);
                break;
            default :
                System.out.println("Invalid operator");
        }



        

    }

}