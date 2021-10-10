package chap15;

import java.io.File;

public class FileEx9 {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: java FileEx9 DIRECTORY");
            System.exit(0);
        }

        File dir = new File(args[0]);

        if(!dir.exists() || !dir.isDirectory()) {
            System.out.println("유효하지 않은 디렉토리입니다. ");
            System.exit(0);
        }

        File[] list = dir.listFiles();

        for(File file: list) {
            String fileName = file.getName();
            String newFileName = "0000" + fileName;
            newFileName = newFileName.substring(newFileName.length() - 7);
            file.renameTo(new File(dir, newFileName));
        }
    }
}
