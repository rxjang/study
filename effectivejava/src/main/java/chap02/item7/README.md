# item7. 다 쓴 객체 참조를 해제하라

자바는 가비지 컬렉터가 자동으로 사용되지 않은 메모리를 탐지해 해지한다. 하지만 몇몇 코드는 실제로 사용하지 않는 코드인데 GC를 속여 메모리를 해지 못하게 한다.


아래 코드에서 메모리 누수를 찾아보자. 
``` java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }
    ...
 }
```
누수가 발생하는 위치는 스택이 커졌다가 줄어들었을 때이다. GC가 꺼내진 객체들을 회수해 가지 않는다. 아래와 같은 수정으로 누수를 막을 수 있다.
``` java
public Object pop() {
    if (size == 0)
        throw new EmptyStackException();
    Object result = elements[--size];
    elements[size] = null; // 다 쓴 참조 객체
    return result;
}
```
**자기 메모리를 직접 관리하는 클래스라면 메모리 누수에 주의해야한다.**
  
메모리 누수를 일으키는 주범들:
* 캐시
* 리스너 혹은 콜백