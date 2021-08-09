package chap07;

public class DefaultMethodTest {
    public static void main(String[] args) {
        Children c = new Children();
        c.method1();
        c.method2();
        MyInterface.staticMethod();
        MyInterface2.staticMethod();
    }
}

class Children extends Parents implements MyInterface, MyInterface2{
    @Override
    public void method1() {
        System.out.println("method1() in Children");
    }
}

class Parents {
    public void method2() {
        System.out.println("method2()  in Parents");
    }
}

interface MyInterface {
    default void method1() {
        System.out.println("method1() in MyInterface");
    }
    default void method2() {
        System.out.println("method2() in MyInterface");
    }
    static void staticMethod() {
        System.out.println("staticMethod() in MyInterface");
    }
}

interface MyInterface2 {
    default void method1() {
        System.out.println("method1() in MyInterface2");
    }
    static void staticMethod() {
        System.out.println("staticMethod() in MyInterface2");
    }
}