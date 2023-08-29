package chap09.item61;

import java.util.Comparator;

public class BoxingTypeExample {
    static Integer i;

    public static void main(String[] args) {
        Comparator<Integer> natureOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
        int compare = natureOrder.compare(new Integer(42), new Integer(42));
        System.out.println(compare);

//        if (i == 42) {
//            System.out.println("???");
//        }

        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
