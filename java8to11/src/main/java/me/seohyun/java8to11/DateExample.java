package me.seohyun.java8to11;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateExample {

    public static void main(String[] args) throws InterruptedException {

        Date date = new Date();
        long time = date.getTime();
        System.out.println(date);
        System.out.println("기계용 시간: " + time);

//        Thread.sleep(1000*3);
        Date after3Seconds = new Date();
        System.out.println(after3Seconds);
        after3Seconds.setTime(time);
        System.out.println(after3Seconds);
        // Date 는 mutable 하다

        Calendar birthDay = new GregorianCalendar(2000, Calendar.SEPTEMBER, 15);
        System.out.println(birthDay.getTime());
        //type safe 하지 않
    }
}
