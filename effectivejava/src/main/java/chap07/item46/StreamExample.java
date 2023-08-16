package chap07.item46;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) throws IOException {

        File file = new File("");

        Map<String, Long> freq = new HashMap<>();
        // 스트림 패러다임일 이해햐지 못한채 API만 사용
        try (Stream<String> words = new Scanner(file).tokens()) {
            words.forEach(word -> {
                freq.merge(word.toLowerCase(), 1L, Long::sum);
            });
        }

        // 스트림을 제대로 활용해 빈도표를 초기화한다.
        Map<String, Long> freq1;
        try (Stream<String> words = new Scanner(file).tokens()) {
            freq1 = words.collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        }

        // 빈도표에서 가장 흔한 단어 10개를 뽑아내는 파이프라인
        List<String> topTen = freq.keySet().stream()
                .sorted(Comparator.comparing(freq::get).reversed())
                .limit(10)
                .collect(Collectors.toList());


    }
}
