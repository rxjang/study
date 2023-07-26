package chap04.item18;

import java.util.List;

public class InstrumentedHashSetTest {

    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(List.of("탁", "탁탁", "펑"));
        System.out.println(s.getAddCount());
    }
}
