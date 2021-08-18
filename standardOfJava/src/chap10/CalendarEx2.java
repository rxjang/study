package chap10;

import java.util.Calendar;

public class CalendarEx2 {
    public static void main(String[] args) {

        final String[] DAY_OF_WEEK = {"", "일", "월", "화", "수", "목", "금", "토"};

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.set(2000, 8, 15);

        long difference = (date2.getTimeInMillis() - date1.getTimeInMillis())/1000;

        System.out.println(difference/(24*60*60));;
    }

}
