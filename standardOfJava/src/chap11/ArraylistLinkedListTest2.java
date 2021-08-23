package chap11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArraylistLinkedListTest2 {
    public static void main(String[] args) {
        ArrayList a1 = new ArrayList(100000);
        LinkedList l1 = new LinkedList();
        add(a1);
        add(l1);

        System.out.println("== 접근시간테스트 ==");
        System.out.println("ArrayList   : " + access(a1));
        System.out.println("LinkedList   : " + access(l1));
    }
    
    public static void add(List list) {
        for(int i=0; i < 10000; i++) list.add(i + "");
    }

    public static long access(List list) {
        long start = System.currentTimeMillis();
        for(int i=0; i< 10000; i++) list.get(i);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
