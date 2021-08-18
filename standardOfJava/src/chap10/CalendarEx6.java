package chap10;

import java.util.Calendar;
import java.util.Scanner;

public class CalendarEx6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("원하는 년도를 입력하세요>>");
        int year = scanner.nextInt();
        System.out.print("원하는 달을 입력하세요>>");
        int month = scanner.nextInt();

        int START_DAY_OF_WEEK = 0;
        int END_DAY = 0;

        Calendar sDay = Calendar.getInstance();
        Calendar eDay = Calendar.getInstance();

        sDay.set(year, month-1, 1);
        eDay.set(year, month, 1);

        eDay.add(Calendar.DATE, -1);

        START_DAY_OF_WEEK = sDay.get(Calendar.DAY_OF_WEEK);

        END_DAY = eDay.get(Calendar.DATE);

        System.out.println("      " + year + "년 " + month + "일 ");
        System.out.println(" SUN MON TUS WED THU FRI SAT");
        for(int i=1; i < START_DAY_OF_WEEK; i++) {
            System.out.print("    ");
        }

        for(int i = 1, n = START_DAY_OF_WEEK; i < END_DAY; i++, n++) {
            System.out.print((i<10)? "   " + i : "  " + i);
            if(n%7 == 0) System.out.println();
        }

    }
}
