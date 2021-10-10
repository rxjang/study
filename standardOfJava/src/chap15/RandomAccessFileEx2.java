package chap15;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileEx2 {
    public static void main(String[] args) {
        int[] score = {1, 100, 90, 90,
                       2, 70, 90, 100,
                        3, 100, 100, 100,
                        4, 70, 60, 80,
                        5, 70, 90, 100
        };

        try {
            RandomAccessFile raf = new RandomAccessFile("score2.dat", "rw");
            for(int i = 0 ; i < score.length; i++) {
                raf.writeInt(score[i]);
                // 포인터의 위치가 파일의 마지막으로 이동됨
            }
            while(true) {
                System.out.println(raf.readInt());
                // 마지막 부분부터 읽기 시작하기 떄문에 아무것도 읽지 못하고 EOFException이 발생해 무한 반복문을 벗어나게 됨
            }
        } catch (EOFException eof) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
