package chap09;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularEx4 {
    public static void main(String[] args) {
        String source = "A broken hand word, but not a broken heart.";
        String pattern = "broken";
        StringBuffer sb = new StringBuffer();

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        System.out.println("source:" + source);

        int i =0;

        while(m.find()) {
            System.out.println(++ i + "번째 매칭: " + m.start() + " ~ " + m.end());
            m.appendReplacement(sb, "drunken");
        }

        m.appendTail(sb);
        System.out.println("Replacement count : " + i);
        System.out.println("result:" + sb.toString());
    }
}
