package chap15;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEx4 {
    public static void main(String[] args) {
        String currDir = System.getProperty("user.dir");
        File dir = new File(currDir);

        File[] files = dir.listFiles();

        for(File file : files) {
            String name = file.getName();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mma");
            String attribute = "";
            String size = "";

            if(file.isDirectory()) {
                attribute = "DIR";
            } else {
                size = file.length() + "";
                attribute = file.canRead() ? "R" : " ";
                attribute += file.canWrite() ? "W" : " ";
                attribute += file.isHidden() ? "H" : " ";
            }
            System.out.printf("%s %3s %6s %s\n", df.format(new Date(file.lastModified())), attribute, size, name);
        }
    }
}
