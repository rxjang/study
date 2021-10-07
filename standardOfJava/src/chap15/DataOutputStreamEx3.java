package chap15;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamEx3 {
    public static void main(String[] args) {
        int[] score = {100, 90, 95, 85, 50};

        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("score.dat"));

            for (int j : score) {
                dos.writeInt(j);
            }

            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
