package me.seohyun.java8to11;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StreamExample {

    public static void main (String[] args) {

        List<String> names = new ArrayList<>();
        names.add("seohyun");
        names.add("ruixian");
        names.add("jang");
        names.add("seohyun Jang");

//        List<String> collect = names.stream().map((s) -> {
//            System.out.println(s);
//            return s.toUpperCase(Locale.ROOT);
//        }).collect(Collectors.toList());
//
//        System.out.println("==================");
//
//        collect.forEach(System.out::println);

        //parallelStream 을 쓴다고 무조건 좋은거 아님 -> 데이터가 정말 방대하고 클 경우 사용
        List<String> strings = names.parallelStream().map( (s) ->{
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase(Locale.ROOT);
        }).collect(Collectors.toList());

        strings.forEach(System.out::println);

    }

}
