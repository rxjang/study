package chap02;

public class CharToCode {
    public static void main(String[] args) {
        char ch = 'A';
        int code = (int) ch;

        System.out.printf("%c=%d(%#X)%n", ch, code, code);

        char hch = '가';
        System.out.printf("%c=%d(%#X)%n", hch, (int)hch, (int)hch);

        System.out.println("abc\t123\b456");    // \b에 의헤 3이 지워짐
    }
}
