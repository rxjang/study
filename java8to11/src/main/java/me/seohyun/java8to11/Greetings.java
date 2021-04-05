package me.seohyun.java8to11;

public interface Greetings {

    void printName();

    /**
     * @implSpec
     * 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    //```  Object 에서 제공하는 method 는 재정의 할 수 없음
//    default String toString() {
//
//    }

    static void printAnything() {
        System.out.println("anything");
    }

    String getName();
}
