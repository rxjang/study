package chap09;

public class ClassEx1 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Card1 c = new Card1("HEART", 3);
        Card1 c2 = Card1.class.newInstance();

        Class cObj = c.getClass();

        System.out.println(c);
        System.out.println(c2);
        System.out.println(cObj.getName());
        System.out.println(cObj.toGenericString());
        System.out.println(cObj.toString());
    }
}

final class Card1 {
    String kind;
    int num;

    public Card1() {
        this("SPADE", 1);
    }

    public Card1(String kind, int num) {
        this.kind = kind;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Card1{" +
                "kind='" + kind + '\'' +
                ", num=" + num +
                '}';
    }
}
