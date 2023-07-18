package chap03.item13;

public class SampleClass implements Cloneable {

    public SampleClass clone() throws CloneNotSupportedException{
        return (SampleClass) super.clone();
    }
}
