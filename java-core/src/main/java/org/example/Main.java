package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main( String[] args )
    {
        findPrimeNumber();
    }

    public static void findSecondMinNumber(){
        Scanner in = new Scanner(System.in);
        int cnt = in.nextInt();
        in.nextLine();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            numbers.add(in.nextInt());
        }


        Optional<Integer> secondMin = numbers.stream()
                .sorted()
                .distinct()
                .skip(1)
                .findFirst();

        System.out.println(secondMin.get());
    }

    public static void findPrimeNumber(){
        Scanner in = new Scanner(System.in);
        Integer numberOfPrimeNumber = in.nextInt();

        if (numberOfPrimeNumber == 1) {
            System.out.println(2);
        }else if (numberOfPrimeNumber == 0){
            System.out.println(1);
        }else {
            String binaryRepresentation = Integer.toBinaryString(numberOfPrimeNumber-1);

            Integer primeNumber = Integer.parseInt(binaryRepresentation + "1", 2);

            System.out.println(primeNumber);
        }
    }
}
