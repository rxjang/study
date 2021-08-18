package chap10;

import java.util.Calendar;
import java.util.Scanner;

public class CalendarEx7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("원하는 년도를 입력하세요>>");
        int year = scanner.nextInt();
        System.out.print("원하는 달을 입력하세요>>");
        int month = scanner.nextInt();

        Calendar sDay = Calendar.getInstance();
        Calendar eDay = Calendar.getInstance();

        sDay.set(year, month-1, 1);
        eDay.set(year, month-1, sDay.getActualMaximum(Calendar.DATE));
        System.out.println(sDay.toString());
        System.out.println(eDay);

        // 1 일이 속한 주의 일요일로 날짜 설정.
        sDay.add(Calendar.DATE, -sDay.get(Calendar.DAY_OF_WEEK) + 1);
        // 말일이 속한 주의 토요일로 날짜 설정
        eDay.add(Calendar.DATE, 7 - eDay.get(Calendar.DAY_OF_WEEK));

        System.out.println("      " + year + "년 " + month + "일 ");
        System.out.println(" SUN MON TUS WED THU FRI SAT");

        for(int n = 1; sDay.before(eDay) || sDay.equals(eDay) ; sDay.add(Calendar.DATE, 1)) {
            int day = sDay.get(Calendar.DATE);
            System.out.print((day < 10)? "   " + day : "  " + day);
            if(n++%7 == 0) System.out.println();
        }

    }
}
