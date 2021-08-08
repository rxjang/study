package chap07;

public class InstanceofTest {
    public static void main(String[] args) {
        FireEngine fe = new FireEngine();

        if(fe instanceof FireEngine) {
            System.out.println("This is a Fireengine instance.");
        }

        if(fe instanceof Car) {
            System.out.println("This is a an Object instance.");
        }

        System.out.println(fe.getClass().getName());
    }
}
