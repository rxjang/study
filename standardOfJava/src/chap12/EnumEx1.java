package chap12;

enum Direction { EAST, SOUTH, WEST, NORTH}

public class EnumEx1 {
    public static void main(String[] args) {
        Direction d1 = Direction.EAST;
        Direction d2 = Direction.valueOf("WEST");
        Direction d3 = Enum.valueOf(Direction.class, "EAST");

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);

        System.out.println(d1==d2);
        System.out.println(d1==d3);
        System.out.println(d1.equals(d3));
        System.out.println(d1.compareTo(d3));
        System.out.println(d1.compareTo(d2));

        Direction[] dArr = Direction.values();
        for(Direction d : dArr) {
            System.out.printf("%s=%d%n", d.name(), d.ordinal());
        }
    }
}
