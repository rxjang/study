# item28. 배열보다는 리스트를 사용하라

## 배열과 제네릭 타입의 차이점
### 공변
배열은 공변이 가능하나, 지네릭은 불공변이다. 
* 여기서 공변이란, Sub가 Super의 하위 타입아라면, Sub[]는 Super[]의 하위타입이 된다.
``` java
// 런타임 에러 발생
Object[] objectArray = new String[1];

// 컴파일 에러 발생
List<Object> objectList = new ArrayList<String>()
```
* 위와 같이 배열의 경우 런타임 시점에 오류를 알아채게 된다. 

### 실체화
배열은 실체화된다. 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다는 뜻이다.   
반면 지네릭은 타입정보가 런타임에는 소거된다. 

## 재네릭 배열을 만들지 못하는 이유
* 타입이 안전하지 않음
* 허용시 컴파일러가 자동 생성한 형변환 코드에서 런타임에 ClassCastException이 발생할 수 있음
* 이는 런타임에 위 예외가 발생하는 일을 막아주겠다는 제네릭 타입 시스템의 취지에 어긋남

## 제네릭 적용 예시
```java
public class Chooser {
    private final Object[] choiceArray;

    public Chooser(Collection choices) {
        choiceArray = choices.toArray();
    }

    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}
```
위 클래스를 사용 할 때 choose()를 호출할 때마다 반환된 Object를 원하는 타입으로 형변환해야한다. 이를 제네릭을 사용해 개선해보자.
```java
public class Chooser<T> {
    private final T[] choiceArray;

    public Chooser(Collection<T> choices) {
        choiceArray = (T[]) choices.toArray(); // 형변환 경고 발생
    }
    ...
}
```
이렇게 수정하면 형변환을 할 필요가 없어진다. 다만, 형변환 경고가 발생한다. List를 사용해 형변환 경고를 없애보자.

```java
public class Chooser<T> {
    private final List<T> choiceList;

    public Chooser(Collection<T> choices) {
        choiceList = new ArrayList<>(choices);
    }

    ...
}
```