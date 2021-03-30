package me.seohyun.java8to11;

@FunctionalInterface
public interface RunSomething {

    /*
     다른 형태의 메소드가 몇 개든 추상 메소드가 하나만 있을 때 함수형 인터페이스라고 함
     @FunctionalInterface 자바가 제공하는 어노테이션: 위반시 컴파일 할 때 에러 -> 인터페이스를 더 견고하게 만듦
     */
    int doIt(int number);

    static void printName() {
        System.out.println("print");
        //인터페이스 임에도 불구하고 static method 선언가능
    }

    default  void printAge() {
        System.out.println("age");
    }

}
