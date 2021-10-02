package chap14;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx6 {
    public static void main(String[] args) {
        Student[] stuArr = {
                new Student("이자바", 3, 300),
                new Student("김자바", 1, 200),
                new Student("안자바", 2, 100),
                new Student("박자바", 2, 150),
                new Student("소자바", 1, 200),
                new Student("나자바", 3, 290),
                new Student("감자바", 3, 180)
        };
        // 학생 이름만 뽑아서 List<String>에 저장
        List<String> names = Stream.of(stuArr).map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(names);

        Student[] stuArr2 = Stream.of(stuArr).toArray(Student[]::new);
        for(Student s: stuArr2) System.out.println(s);

        Map<String, Student> stuMap = Stream.of(stuArr)
                .collect(Collectors.toMap(Student::getName, p->p));
        for(String name: stuMap.keySet())
            System.out.println(name + "-" + stuMap.get(name));

        long count = Stream.of(stuArr).count();
        long totalScore = Stream.of(stuArr).collect(Collectors.summingInt(Student::getTotalScore));

        System.out.println("count = " + count);
        System.out.println("totalScore = " + totalScore);

        String stuNames = Stream.of(stuArr).map(Student::getName).collect(Collectors.joining(",", "{", "}"));
        System.out.println(stuNames);
    }

}
