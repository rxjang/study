package chap14;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx7 {

    public static void main(String[] args) {
        Students[] stuArr = {
                new Students("이자바", true, 1, 1, 300),
                new Students("김자바", false, 1, 1, 200),
                new Students("안자바", true, 2, 1,280),
                new Students("박자바", false, 2, 1, 150),
                new Students("소자바", true ,1,2, 200),
                new Students("나자바", false, 1,3, 290),
                new Students("감자바", true,2, 2, 180)
        };

        System.out.printf("1. 단순분할(성별로 분할)%n");
        Map<Boolean, List<Students>> stuBySex = Stream.of(stuArr).collect(Collectors.partitioningBy(Students::isFemale));

        List<Students>  femaleStudent = stuBySex.get(true);
        List<Students> maleStudent = stuBySex.get(false);

        for(Students s : femaleStudent) System.out.println(s);
        for(Students s: maleStudent) System.out.println(s);

        System.out.printf("2. 단순분할 + 통계 ( 성별 학생 수 ) %n");
        Map<Boolean, Long> stuNumBySex = Stream.of(stuArr).collect(Collectors.partitioningBy(Students::isFemale, Collectors.counting()));

        System.out.println("여학생 수 : " + stuNumBySex.get(true));
        System.out.println("남학생 수 : " + stuNumBySex.get(true));

        System.out.printf("3. 단순분할 + 통계 ( 성별 1등 ) %n");
        Map<Boolean, Students> topScoreBySex = Stream.of(stuArr)
                .collect(Collectors.partitioningBy(Students::isFemale,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Students::getScore)), Optional::get)));

        System.out.println("여학생 1등 : " + topScoreBySex.get(true));
        System.out.println("남학생 1등 : " + topScoreBySex.get(false));

    }
}

class Students {
    String name;
    boolean isFemale;
    int hak;
    int ban;
    int score;

    public Students(String name, boolean isFemale, int hak, int ban, int score) {
        this.name = name;
        this.isFemale = isFemale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public int getHak() {
        return hak;
    }

    public int getBan() {
        return ban;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isFemale? "여" : "남", hak, ban, score);
    }

    enum Level { HIGH, MID, LOW}
}
