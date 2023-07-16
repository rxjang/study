package chap03.item11;

//import chap03.item10.PhoneNumber;

import java.util.HashSet;
import java.util.Set;

public class HashCodeTest {
    public static void main(String[] args) {
        PhoneNumber phoneNumber1 = new PhoneNumber(82, 123, 456);
        PhoneNumber phoneNumber2 = new PhoneNumber(82, 123, 456);
        Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
        phoneNumbers.add(phoneNumber1);
        System.out.println(phoneNumber1.equals(phoneNumber2));
        System.out.println(phoneNumbers.contains(phoneNumber2));
    }
}
