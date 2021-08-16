package chap09;
import java.util.function.DoubleToIntFunction;

import static java.lang.Math.*;

public class MathEx2 {
    public static void main(String[] args) {
        int i = Integer.MIN_VALUE;

        System.out.println("i =" + i);
        System.out.println("-i =" + (-i));

        try{
            System.out.printf("negateExact(%d)= %d%n", 10, negateExact(10));
            System.out.printf("negateExact(%d)= %d%n", -10, negateExact(-10));
            System.out.printf("negateExact(%d)= %d%n", i, negateExact(i));
        } catch (ArithmeticException e) {
            System.out.println("Exception");
            System.out.printf("negateExact(%d) = %d%n", (long)i, negateExact((long)i));
        }
    }
}
