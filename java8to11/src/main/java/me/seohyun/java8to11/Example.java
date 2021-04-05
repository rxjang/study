package me.seohyun.java8to11;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class Example {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("seohyun");
        names.add("ruixian");
        names.add("jang");
        names.add("seohyun Jang");

        names.sort(String::compareToIgnoreCase);

        // 쪼갤 수 있는 기능을 가진 iterator
        Spliterator<String> spliterator = names.spliterator();
        Spliterator<String> spliterator1 = spliterator.trySplit();
        while(spliterator.tryAdvance(System.out::println));
        System.out.println("================");
        while(spliterator1.tryAdvance(System.out::println));

    }
}
