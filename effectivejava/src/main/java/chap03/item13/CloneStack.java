package chap03.item13;

import java.util.Arrays;
import java.util.EmptyStackException;

public class CloneStack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public CloneStack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public Object[] getElements() {
        return elements;
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
        try {
            CloneStack result = (CloneStack) super.clone();
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
