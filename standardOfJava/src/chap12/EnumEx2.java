package chap12;

enum Directions {
    EAST(1, ">"), SOUTH(2, " V"), WEST(3,">"), NORTH(4, "^");

    private static final Directions [] DIR_ARR = Directions.values();
    private final int value;
    private final String symbol;

    Directions(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Directions of(int dir) {
        if(dir < 1 || dir >4) {
            throw new IllegalArgumentException("Invalid value : " + dir);
        }

        return DIR_ARR[dir -1];
    }

    public Directions rotate(int num) {
        num = num % 4;

        if(num < 0) num +=4;
        return DIR_ARR[(value-1+num)%4];
    }

    public String toString() {
        return name()+getSymbol();
    }
}

public class EnumEx2 {
    public static void main(String[] args) {
        for(Directions d : Directions.values()) {
            System.out.printf("%s=%d%n", d.name(), d.getValue());
        }

        Directions d1 = Directions.EAST;
        Directions d2 = Directions.of(1);
        System.out.println(d1.name() + d1.getValue());
        System.out.println(d2.name() + d2.getValue());

        System.out.println(Directions.EAST.rotate(1));
        System.out.println(Directions.EAST.rotate(2));
        System.out.println(Directions.EAST.rotate(-1));
        System.out.println(Directions.EAST.rotate(-2));
    }
}
