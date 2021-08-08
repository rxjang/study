package chap07;

public class PolyArgumentTest {
    public static void main(String[] args) {
        Buyer b = new Buyer();
        Computer com  = new Computer();

        b.buy(new Tv());
        b.buy(com);
        b.buy(new Audio());
        b.summary();
        System.out.println();
        b.refund(com);
        b.summary();

        System.out.println("현재 남은 돈은 " + b.money + "만원 입니다.");
        System.out.println("현재 보너스 점수는 " + b.bonusPoint +  "점입니다.");
    }
}
