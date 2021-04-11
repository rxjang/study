package me.seohyun.java8to11;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeExample {

    public static void main(String[] args) {
        //기계적 시간 ->의전의 date 와 비슷
        Instant instant = Instant.now();
        System.out.println(instant);    //기준시 UTC, GMT

        ZoneId zone = ZoneId.systemDefault();
        System.out.println(zone);
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        System.out.println(zonedDateTime);

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println(now.format(formatter));

         LocalDateTime birthDay = LocalDateTime.of(2000, Month.SEPTEMBER, 15, 0, 0, 8);
        System.out.println(birthDay);

        ZonedDateTime nowInShanghai = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(nowInShanghai);

        //기간을 표현하는 법
        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthDay = LocalDate.of(2021, Month.SEPTEMBER, 15);

        // human Time 용
        Period period = Period.between(today, thisYearBirthDay);
        System.out.println(period.getDays());

        Period until = today.until(thisYearBirthDay);
        System.out.println(until.get(ChronoUnit.DAYS));

        //MachineTime 용
        Instant nowInstant = Instant.now();
        Instant plus = nowInstant.plus(10, ChronoUnit.SECONDS);
        Duration between = Duration.between(nowInstant, plus);
        System.out.println(between.getSeconds());

        Date date = new Date();
        Instant instant1 = date.toInstant();
        Date newDate = Date.from(instant1);

    }
}
