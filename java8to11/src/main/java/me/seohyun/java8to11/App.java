package me.seohyun.java8to11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class App {

    public static void main(String[] args) {

//        UnaryOperator<String> hi = (s) -> "hi " + s;

        // 특정 클래싀의 static method 를 사용하는 법
        UnaryOperator<String> hi =  Greeting::hi;

        // 클래스의 instance method 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;
        System.out.println(hello.apply("me"));

        // 실제로 instance 를 만드는 것은 아님
        Supplier<Greeting> newGreeting = Greeting::new;
        newGreeting.get();

        Function<String, Greeting> youGreeting = Greeting::new;

        Greeting you = youGreeting.apply("You");
        System.out.println(you.getName());

        String [] names = {"me", "you", "they"};
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));
    }
}
