package me.seohyun.java8to11;

public interface Bar {


    //동일한 명의 method 가 있을 때 컴파일 에러 발생
    default void printNameUpperCase() {
        System.out.println("BAR");
    }
}
