package chap10;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatEx3 {
    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");

        try{
            Date d = df.parse("2015년 11월 23일");
            System.out.println(df2.format(d));
        } catch (Exception e) {}
    }
}
