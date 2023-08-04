# Item 32. 제네릭과 가변인수를 함께 쓸 때는 신중하라.

## 가변인수 메서드

메서드에 넘기는 인수의 개수를 클라이언트가 조절할 수 있게 하는 것

```java
void sum(String...str) {
	for(String a:str)
    	System.out.println(a);
}
```

- 가변이수 메서드 호출시, 가벼인수를 담기 위한 배열이 자동으로 만들어짐
- 제네릭과 가변인수를 함께 사용하게 되면 제네릭 배열이 생성됨
- but, 힙오염이 발생하며, 컴파일 경고가 나옴

```java
public class Dangerous {
    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42);
        Object[] objects = stringLists;
        objects[0] = intList; // 힙 오염 발생
        String s = stringLists[0].get(0); // ClassCastException
    }

    public static void main(String[] args) {
        dangerous(List.of("There be dragons!"));
    }
}
```

위의 예시처럼 타입 안정성이 깨지기 때문에, **제네릭 varargs 매개변수에 값을 저장하는 것은 안전하지 않다.**

Q. 제네릭 배열을 직접 선언하는 것은 허용하지 않으면서 제네릭 varargs 매개변수를 받는 메서드의 선언을 허용한 이유는 무엇일까?

A. 제네릭 매개변수화 타입의 varargs 매개변수를 받는 매서드는 실무에서 유용하기 때문에 모순을 허용함. 타입 안전이 보장된다면 `@SafeVarags`를 통해 컴파일러가 경고를 하지 않게 만들 수 있음

## @SafeVarags

메서드가 타입 안정한지 보장할 수 있게 해주는 어노테이션. 컴파일러 경고를 없앨 수 있음

- 재정의 할 수 없는 메서드에만 달아야 함

### 메서드를 안전하다고 판단할 수 있는 경우

1. varargs 매개변수 배열에 아무것도 저장하지 않는다.
2. varargs 매개변수 배열 혹은 복제본의 참조가 밖으로 노출되지 않는다.