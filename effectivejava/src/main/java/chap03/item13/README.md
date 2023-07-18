# item13. clone 재정의는 주의해서 진행하라.
clone()을 잘 동작하게끔 해주는 구현 방법과 언제 그렇게 해야하는지를 알아보자. 

## Cloneable인터페이스의 역할
* Object의 clone의 동작 방식을 결정
* clone 호출시 그 객체의 필드들을 하나하나 복사한 객체를 반환
* cloneable을 구현하지 않은 클래스 호출시 CloneNotSupportedException을 던짐
* 상위 클래스에 정의된 protected clone()의 동작 방식을 변경한 것

## clone()의 일반 규약
* `x.clone() != x` 는 참이어야 한다.
* `x.clone().getClass() == x.getClass()`는 참이어야 한다.
* `x.clone().equals(x)`는 참이어야 한다. 
* x와 반환된 객체 y는 서로 독립적이어야 한다. 복사 이후 객체 x의 값을 변환하여도 복사본 y의 값은 변하지 않는다.

## 참조 타입을 필드로 갖는 객체의 cloneable 구현
아이템 7의 Stack에 Cloneable을 구현해보자.
```java
public class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
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
        return super.clone();
    }
}
```
* 이 경우 `size` 필드는 정확히 복제하지만, `elements` 필드는 복제 후에도 원본 인스턴스와 똑같은 배열을 참조한다. 
* 제대로 동작시키기 위해선 `elements` 배열의 clone()을 재귀적으로 호출해주는 것이다. 

``` java
@Override 
public Stack clone() {
  try {
    Stack result = (Stack) super.clone();
    result.elements = elements.clone();
    return result;
  } catch (CloneNotSupportedException e) {
    throw new AssertionError();
  }
}
```
위 방법은 `elements`필드가 final이었다면 사용할 수 없다. final 필드에는 새로운 값을 할당할 수 없기 떄문이다. 
이는 **가변 객체를 참조하는 필드는 final로 선언하라**는 일반용법과 충돌한다. 또한 위 방법으로 객체 복제가 불가능한 경우도 있다. 
``` java
public class HashTable implements Cloneable {
  private Entry[] buckets = ...;
  private static class Entry {
    final Object key;
    Object value;
    Entry next;
    // contstructor...
  }

  // 잘못된 clone 메서드 예
  @Override public HashTable clone() {
    try {
      HashTable result = (HashTable) super.clone();
      result.buckets = buckets.clone();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
```
위 HashTable은 원본과 같은 값을 참하게 된다. 이는 아래와 같이 **깊은 복사**를 통해 해결 가능하다.

``` java
public class HashTable implements Cloneable {
  private Entry[] buckets = ...;
  private static class Entry {
    final Object key;
    Object value;
    Entry next;
    // constructor
  }
  
  Entry deepCopy() {
    return new Entry(key, value, next == null ? null : next.deepCopy());
  }
  
  @Override public HashTable clone() {
    try {
      HashTable result = (HashTable) super.clone();
      result.buckets = new Entry[buckets.length];
      for (int i = 0; i < buckets.length; i++) 
        if (buckets[i] != null)
          result.buckets[i] = buckets[i].deepCopy();
      return result;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
```
단순히 생성자를 호출하도록 해 복제를 진행했다. 

## 복사 생성자와 복사 팩터리
``` java
// 복사 생성자
public CopyClass(CopyClass copyClass) {
    ...
}

// 복사 팩터리
public static CopyClass newInstance(CopyClass copyClass) {
    ...
}
```
복사 생성자와 복사 팩터리는 아래의 측면에서 Cloneable/clone보다 낫다
* 위험한 객체 생성 메터니즘을 사용하지 않는다.
* 엉성하게 문서화된 규약에 기대지 않는다.
* 정상적인 final 필드 용법과 충돌하지 않는다.
* 불필요한 검사 예외를 던지지 않고, 형변환도 필요치 않다.
* 인터페이스 타입의 인스턴스를 인수로 받을 수 있다.

## 결론
* 새로운 인터페이스를 만들 때 절대 Cloneable을 확장해서는 안되고 새로운 클래스도 이를 구현해서는 안된다.
* 복제 기능은 생성자와 팩터리를 이용하자.
* 단, 배열만은 clone 메서드 방식이 깔끔하다. 