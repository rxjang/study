package chap11;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesEx2 {
    public static void main(String[] args) {
        Properties prop = new Properties();

        prop.setProperty("timeout", "30");
        prop.setProperty("language", "한글");
        prop.setProperty("size", "10");
        prop.setProperty("capacity","10");

        try{
            prop.store(new FileOutputStream("output.txt"), "properties example");

            prop.storeToXML(new FileOutputStream("output.xml"), "properties example");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
