package chap15;

import java.io.File;

public class FileEx8 {
    static int deleteFiles = 0;

    public static void main(String[] args) {
        if(args.length !=1) {
            System.out.println("USAGE :  java FileEx8 Extension");
            System.exit(0);
        }

        String currDir = System.getProperty("user.dir");

        File dir = new File(currDir);
        String ext = "." + args[0];

        delete(dir, ext);
        System.out.println(deleteFiles + "개의 파일이 삭제되었습니다.");
    }

    public static void delete(File dir, String ext) {
        File[] files = dir.listFiles();

        for(File file: files) {
            if(file.isDirectory()) {
                delete(file, ext);
            } else {
                String fileName = file.getAbsolutePath();
                if(fileName.endsWith(ext)) {
                    System.out.print(fileName);
                    if(file.delete()) {
                        System.out.println(" - 삭제 성공");
                        deleteFiles++;
                    } else {
                        System.out.println(" - 삭제 실패");
                    }
                }
            }
        }
    }
}
