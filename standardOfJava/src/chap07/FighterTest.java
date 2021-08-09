package chap07;

public class FighterTest {
    public static void main(String[] args) {
        Fighter f = new Fighter();

        if(f instanceof  Unit)
            System.out.println("f는 Unit클래스의 자손입니다.");
        if(f instanceof Fightable)
            System.out.println("f는 fightable인터페이스를 구현했습니다.");


    }

}
