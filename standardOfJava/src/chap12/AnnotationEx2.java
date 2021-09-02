package chap12;

public class AnnotationEx2 {
    public static void main(String[] args) {
        NewClass nc = new NewClass();
        nc.oldField = 10;
        System.out.println(nc.getOldField());
    }

}

class NewClass {
    int newField;

    int getNewField() { return newField;}

    @Deprecated
    int oldField;

    int getOldField() { return oldField;}
}
