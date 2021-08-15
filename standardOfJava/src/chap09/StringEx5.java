package chap09;

import java.io.UnsupportedEncodingException;
import java.util.StringJoiner;

public class StringEx5 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "가";

        byte[] bArr = str.getBytes("UTF-8");
        byte[] bArr2 = str.getBytes("CP949");

        System.out.println(joinByteArr(bArr));
        System.out.println(joinByteArr(bArr2));
    }

    static String joinByteArr(byte[] bArr) {
        StringJoiner sj = new StringJoiner(":", "[", "]");

        for(byte b : bArr) {
            sj.add(String.format("%02X", b));
        }
        return sj.toString();
    }
}
