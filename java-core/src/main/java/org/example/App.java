package org.example;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        findSecondMinNumber();
    }

    public static void findSecondMinNumber(){
        Scanner in = new Scanner(System.in);
        int cnt = in.nextInt();
        in.nextLine();

        int number;
        int minNumber = in.nextInt();
        int secondMinNumber = minNumber;
        for (int i = 0; i < cnt-1; i++) {
            number = in.nextInt();
            if (number < minNumber ){
                secondMinNumber = minNumber;
                minNumber = number;
            }else if (minNumber == secondMinNumber && number > secondMinNumber){
                secondMinNumber = number;

            }else if (minNumber != secondMinNumber && secondMinNumber > number){
                secondMinNumber = number;
            }
        }

        System.out.println(secondMinNumber);
    }
}
