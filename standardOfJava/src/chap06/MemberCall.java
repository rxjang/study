package chap06;

public class MemberCall {
    int iv = 10;
    static int cv = 2;

    int iv2 = cv;
//    static int cv2 = iv; // 에러: 클래스 변수는 인스턴스 변수를 사용할 수 없음
    static int cv2 = new MemberCall().iv; // 이처럼 객체를 생성해야 사용가능

    static void staticMethod1() {
        System.out.println(cv);
//        System.out.println(iv);   // 에러: 클래스메서드에서 인스턴스 변수는 사용 불가
        MemberCall c = new MemberCall();
        System.out.println(c.iv);   // 객체를 생성한 후에야 인스턴스 변수 참조 가능
    }

    void instanceMethod() {
        System.out.println(cv);
        System.out.println(iv);
    }

    static void staticMethod2() {
        staticMethod1();
//        instanceMethod();   //에러: 클래스 메서드에서는 인스턴스 메서드를 호출 할 수 없음
        MemberCall c = new MemberCall();
        c.instanceMethod();
    }

    void instanceMethod2() {
        staticMethod1();
        instanceMethod();
    }

}
