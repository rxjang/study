package me.seohyun.java8to11;

import org.springframework.boot.SpringApplication;

public class Foo {

    public static void main(String[] args) {

//        RunSomething runSomething = new RunSomething() {
//            // 익명 내부 클래스 anonymous inner class
//            @Override
//            public void doIt() {
//                System.out.println("Hello");
//            }
//        };

        //람다 표현식
        RunSomething runSomething = (number) -> number + 10;

        System.out.println(runSomething.doIt(1));
    }
}
