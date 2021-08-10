package chap07;

public class InnerEx3 {
    private int outerIv = 0;
    static int outerCv = 0;

    class InstanceInner {
        int iiv = outerIv;
        int iiv2 = outerCv;
    }

    static class StaticInner {
//        int siv = outerIv;    // 에러 스태틱클래스는 외부 클래스의 인스턴스 멤버에 접근 할 수 없다.
    }

    void myMethod() {
        int lv = 0;
        final int LV = 0; // JDK 1.8  부터 생략 가능

        class LocalInner {
            int liv = outerIv;
            int liv2 = outerCv;

            int liv3 = lv;
            int liv4 = LV;
        }
    }
}
