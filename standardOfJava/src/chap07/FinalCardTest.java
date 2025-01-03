package chap07;

class Card {
    final int NUMBER;
    final String KIND;
    static int width = 100;
    static int height = 250;

    Card() {
        this("HEART", 1);
    }

    Card(String kind, int num) {
        KIND = kind;
        NUMBER = num;
    }

    public String toString() {
        return KIND + " " + NUMBER;
    }
}

public class FinalCardTest {

    public static void main(String[] args) {
        Card c = new Card("HEART", 10);
        System.out.println(c.KIND);
        System.out.println(c.NUMBER);
        System.out.println(c);
    }
}
