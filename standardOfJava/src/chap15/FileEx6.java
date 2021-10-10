package chap15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileEx6 {
    static int found = 0;

    public static void main(String[] args) {
        if(args.length !=2) {
            System.out.println("USAGE : java FileEx6 DIRECTORY KEYWORD");
            System.exit(0);
        }
        File dir = new File(args[0]);
        String keyword = args[1];

        if(!dir.exists() || !dir.isDirectory()) {
            System.out.println("유효하지 않은 디렉토리입니다.");
            System.exit(0);
        }

        try {
            findInFiles(dir, keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("총 " + found + "개의 라인에서 '" + keyword + "'을/를 발견했습니다");
    }

    public static void findInFiles(File dir, String keyword) throws IOException {
        File[] files = dir.listFiles();

        for(File file: files) {
            if(file.isDirectory()) {
                findInFiles(file, keyword);
            } else {
                String fileName = file.getName();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                extension = "," + extension + ",";
                if(!",java,rxr,bak".contains(extension)) continue;
                fileName = dir.getAbsolutePath() + File.separator + fileName;

                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);

                String data = "";
                int lineNum = 0;

                while((data = br.readLine()) != null) {
                    lineNum++;
                    if(data.contains(keyword)) {
                        found++;
                        System.out.println("[" + fileName + "(" +lineNum +") ]" + data);
                    }
                }
                br.close();
            }
        }
    }
}
