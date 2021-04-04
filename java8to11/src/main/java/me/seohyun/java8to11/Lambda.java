package me.seohyun.java8to11;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class Lambda {

    public static void main (String[] args) {

        /*
        Supplier 은 Lazy Evaluation 이 가능 -> 불필요한 연산을 피하기 위해 연산을 지연시키는 것
         */
        Supplier<Integer> get10 = () -> 10;

        // Integer 은 빼도됨 -> 자바에셔 추론
        BinaryOperator<Integer> sum = (Integer a, Integer b) -> a+ b;

        Lambda lambda = new Lambda();
        lambda.run();

    }

    private void run() {
        // 자바 8부터 사실상 final 일 경우 final 생략 가능 -> effective final 이라고 함
        final int baseNumber = 10;

        // 로컬 클래스
        class LocalClass{
            void printBaseNumber() {
                // 로컬 클래스 내부의 변수가 위의 변수를 가림
                int baseNumber = 11;
                System.out.println(baseNumber); //11
            }
        }

        // 익명 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber);
            }
        };

        // 람다는 쉐도잉이 없음  -> run 과 같은 Scope
        IntConsumer printInt = (i) -> System.out.println(i + baseNumber);

        printInt.accept(10);
    }
}
