package chap14;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx8 {
    public static void main(String[] args) {

        Students[] stuArr = {
                new Students("이자바", true, 1, 1, 300),
                new Students("김자바", false, 1, 1, 200),
                new Students("안자바", true, 2, 1,280),
                new Students("박자바", false, 2, 1, 150),
                new Students("소자바", true ,1,2, 200),
                new Students("나자바", false, 1,3, 230),
                new Students("감자바", true,2, 2, 180),

                new Students("차자바", false, 2, 2, 50),
                new Students("키자바", true ,1,3, 95),
                new Students("티자바", false, 2,3, 60),
                new Students("파자바", true,2, 3, 90)
        };

        System.out.printf("1. 단순그룹화 (뱐별로 그룹화)%n");
        Map<Integer, List<Students>> stuByBan = Stream.of(stuArr).collect(Collectors.groupingBy(Students::getBan));

        for(List<Students> ban : stuByBan.values()) {
            for(Students s: ban) System.out.println(s);
        }

        System.out.printf("2. 단순그룹화 (성적별별로 그룹화)%n");
        Map<Students.Level, List<Students>> stuByLevel = Stream.of(stuArr)
                .collect(Collectors.groupingBy(s -> {
                    if(s.getScore() >= 200) return Students.Level.HIGH;
                    else if (s.getScore() >= 100) return Students.Level.MID;
                    else return Students.Level.LOW;
                }));

        TreeSet<Students.Level> keySet = new TreeSet<>(stuByLevel.keySet());

        for(Students.Level key : keySet) {
            System.out.println("[" + key + "]");

            for(Students s : stuByLevel.get(key)) System.out.println(s);
            System.out.println();
        }

        System.out.printf("4. 다중그룹화 (학년별, 반별)%n");
        Map<Integer, Map<Integer, List<Students>>> stuByHakAndBan =
                Stream.of(stuArr).collect(Collectors.groupingBy(Students::getHak, Collectors.groupingBy(Students::getBan)));

        for(Map<Integer, List<Students>> hak : stuByHakAndBan.values()) {
            for(List<Students> ban : hak.values()) {
                System.out.println();
                for(Students s : ban) {
                    System.out.println(s);
                }
            }
        }

    }
}
