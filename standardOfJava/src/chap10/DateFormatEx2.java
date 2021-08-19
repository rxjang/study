package chap10;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatEx2 {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(2005, 9, 3);

        Date day = cal.getTime();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(sdf1.format(day));
    }
}
