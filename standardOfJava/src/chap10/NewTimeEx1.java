package chap10;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class NewTimeEx1 {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalDate birthDate = LocalDate.of(2000, 9, 15);
        LocalTime birthTime = LocalTime.of(23, 59, 59);

        System.out.println(today);
        System.out.println(now);
        System.out.println(birthDate);
        System.out.println(birthTime);

        System.out.println(birthDate.withYear(2001));
        System.out.println(birthDate.plusDays(16));
        System.out.println(birthDate.plus(1, ChronoUnit.DAYS));

        System.out.println(birthTime.truncatedTo(ChronoUnit.HOURS));

        System.out.println(ChronoField.CLOCK_HOUR_OF_DAY.range());
        System.out.println(ChronoField.HOUR_OF_DAY.range());

    }
}
