package chap03.item13;

public class StackTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        CloneStack stack = new CloneStack();
        int element = 1;
        stack.push(element);

        CloneStack cloneStack = (CloneStack) stack.clone();
        cloneStack.pop();
        System.out.println(stack.getElements());
        System.out.println(cloneStack.getElements());
    }
}
