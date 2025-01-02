package decorator.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputTest {

    public static void main(String[] args) throws IOException {
        int c ;
        try {

            //TODO 파일 위치 바꿔서 다시해보기
            InputStream in = new LowerCaseInputStream(new BufferedInputStream( new FileInputStream("test.txt")));

            while ((c = in.read()) >= 0) {
                System.out.println((char)c);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
