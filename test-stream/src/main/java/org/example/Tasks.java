package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

public class Tasks {
    public static void main(String[] args) {
        firstTask();

        secondTask();

        sixthTask();

        eithTask();
    }

    private static void firstTask(){
//        Дан список строк, необходимо выбрать строки длиной больше 3 символов и преобразовать их в верхний регистр.
        List<String> strings = Arrays.asList("abc", "abcd", "abcde", "ab");
        strings.stream()
                .filter(s -> s.length() > 3)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    private static void secondTask(){
//        Дан список чисел, подсчитайте количество положительных чисел.
        List<Integer> numbers = Arrays.asList(-2, 0, 1, 3, -1);

        long count = numbers.stream()
                .filter(n -> n > 0)
                .count();

        System.out.println(count);
    }

    private static void thirdTask(){
//        Дан список чисел, найдите максимальное значение
        List<Integer> numbers = Arrays.asList(-2, 0, 1, 3, -1);

        Optional<Integer> maxNumber = numbers.stream()
                .max(Integer::compareTo);
    }

    private static void forthTask(){
//        Дан список чисел, найдите их сумму.
        List<Integer> numbers = Arrays.asList(-2, 0, 1, 3, -1);

        long sum = numbers.stream()
                .reduce(0, Integer::sum);
    }

    private static void fifthTask(){
//        Дан список списков чисел, преобразуйте его в список всех чисел
        List<List<Integer>> listOfLists = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5));

        List<Integer> listOfNumbers = listOfLists.stream()
                .flatMap(List::stream)
                .toList();
    }

    private static void sixthTask(){
//        Дан список сотрудников (имя, возраст, зарплата), отберите сотрудников старше 30 лет с зарплатой выше 50000 и отсортируйте их по имени.
        @AllArgsConstructor
        @Getter
        @ToString
        class Employee {
            String name;
            int age;
            double salary;

            // constructor, getters, setters
        }

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("user1", 29, 6000));
        employees.add(new Employee("user2", 31, 4000));
        employees.add(new Employee("user3", 32, 6000));
        employees.add(new Employee("xy", 32, 6000));

        List<Employee> result = employees.stream()
                .filter(employee -> employee.age > 30 && employee.salary > 5000)
                .sorted(Comparator.comparing(Employee::getName))
                .toList();

        result.forEach(System.out::println);


    }

    private static void seventhTask(){
//        Дан список сотрудников (имя, возраст, зарплата), отберите сотрудников старше 30 лет с зарплатой выше 50000 и отсортируйте их по имени.
        @AllArgsConstructor
        @Getter
        @ToString
        class Student {
            String name;
            String group;

            // constructor, getters, setters
        }

        List<Student> students = new ArrayList<>();
        students.add(new Student("user1", "1"));
        students.add(new Student("user2", "2"));
        students.add(new Student("user3", "1"));
        students.add(new Student("user4", "2"));

        Map<String, List<Student>> groups = students.stream()
                        .collect(Collectors.groupingBy(Student::getGroup));
        

    }

    private static void eithTask(){
//        Дан список чисел, вычислите сумму квадратов всех чисел параллельно.

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        int sumOfSquares = numbers.parallelStream()
                .mapToInt(n -> n*n)
                .sum();
        System.out.println(sumOfSquares);
    }


}
