package Adapter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class IteratorTestDrive {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();

        Iterator<Integer> iterator = list.iterator();

        Enumeration enumeration = new IteratorEnumeration(iterator);

        System.out.println(enumeration.hasMoreElements());

    }


}
