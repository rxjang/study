package chap06;

public class MyMath2 {
    long a, b;

    // 인스턴스 변수
    long add() { return a + b;}

    static long add(long a, long b) { return a+ b;}
}

class MyMathTest2 {
    public static void main(String[] args) {
        System.out.println(MyMath2.add(1L, 2L));

        MyMath2 mm = new MyMath2();
        mm.a = 3L;
        mm.b = 4L;
        System.out.println(mm.add());
    }
}
