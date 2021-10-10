package chap15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileMerge {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("USAGE : java FileMerge filename");
            System.exit(0);
        }

        String mergeFilename = args[0];

        try {
            File tempFile = File.createTempFile("~mergetemp", ".tmp");
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            BufferedInputStream bis = null;

            int number = 1;

            File file = new File(mergeFilename + "_." + number);

            while(file.exists()) {
                file.setReadOnly();
                bis = new BufferedInputStream(new FileInputStream(file));

                int data = 0;
                while((data = bis.read()) != -1) {
                    bos.write(data);
                }
                bis.close();
                file = new File(mergeFilename + "_." + ++number);
            }
            bos.close();

            File oldFile = new File(mergeFilename);
            if(oldFile.exists()) {
                oldFile.delete();
            }
            tempFile.renameTo(oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
