package chap07;

public class CastingTest1 {
    public static void main(String[] args) {
        Car car = null;
        FireEngine fe = new FireEngine();
        FireEngine fe2 = null;

        fe.water();
        car = fe;
//        car.water();  //컴파일 에러 Car 타입의 참조변수로는 water()를 호출할 수 없다.
        fe2 = (FireEngine) car;
        fe2.water();
    }
}
