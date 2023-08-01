# item29. 이왕이면 제네릭 타입으로 만들라
* 클라이언트에서 직접 형변환해야하는 타입보다 제네릭 타입이 더 안전하고 쓰기 편하다.
* 새로운 타입을 설계할 때는 형변환 없이도 사용할 수 있도록하자. -> 제네릭 이용
* 기존 타입중 제네릭이 있어야 하는게 있다면 제네릭 타입으로 변경하자.

## 변형 예시
### Object기반 Stack
```java
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
	
    public void push(Object e) {
        this.ensureCapacity();
        this.elements[this.size++] = e;
    }
	
    public Object pop() {
        if(this.size == 0) {
            throw new EmptyStackException();
        }
        Object result = this.elements[--this.size];
        this.elements[this.size] = null;
        return result;
    }
	
    public boolean isEmpty() {
        return this.size == 0;
    }
	
    private void ensureCapacity() {
        if(this.elements.length == size) {
            this.elements = Arrays.copyOf(this.elements, this.size * 2 + 1);
        }
    }
}
```
이 Stack은 객체를 형변환 하면서 런타임 오류가 날 위험이 있다.

### 제네릭을 사용한 Stack
```java
public class Stack<T> {
    private T[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    @SuppressWarnings("unchecked")
    public Stack() {
        this.elements = (T[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }
	
    public void push(T e) {
        this.ensureCapacity();
        this.elements[this.size++] = e;
    }
	
    public T pop() {
        if(this.size == 0) {
            throw new EmptyStackException();
        }
        T result = this.elements[--this.size];
        this.elements[this.size] = null;
        return result;
    }
	
    public boolean isEmpty() {
        return this.size == 0;
    }
	
    private void ensureCapacity() {
        if(this.elements.length == size) {
            this.elements = Arrays.copyOf(this.elements, this.size * 2 + 1);
        }
    }
}
```
제네릭을 활용해 elements는 클래스 내부에서만 사용하고, 값을 넣고 꺼낼 때 T 타입을 사용하므로 안전하다.