package chap05.item26;

import java.util.ArrayList;
import java.util.List;

public class RawTypeTest {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        String s = strings.get(0);
    }

    public static void unsafeAdd(List list, Object o) {
        list.add(o);
    }
}
