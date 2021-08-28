package chap11;

import java.util.TreeSet;

public class TreeSetEx1 {
    public static void main(String[] args) {
        TreeSet set = new TreeSet();

        String from = "b";
        String to = "d";

        set.add("abc");
        set.add("alien");
        set.add("bat");
        set.add("car");
        set.add("Car");
        set.add("dance");
        set.add("dzzz");
        set.add("fan");
        set.add("elevator");

        System.out.println(set);
        System.out.println(set.subSet(from, to));
        System.out.println(set.subSet(from, to + "zzz"));
    }
}
