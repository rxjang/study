# item 26. 로 타입은 사용하지 말라

## 제네릭 클래스, 제네릭 인터페이스
클래스와 인터페이스 선언에 타입 매개변수가 쓰이는 경우
* 클래스의 멤버 변수느 메소드 파라미터의 타입을 고정하지 ㅇ낳고 사용할 수 있다.

```java
public class Machine<T> {
    private T machine;
    
    public Machine(T machine) {
        this.machine = machine;
    }
    
    public void work() {
        System.out.println(machine);
    }
}
```

## raw type
제네릭 타입에서 타입 매개변수를 전혀 사용하지 않는 경우
* 제네릭이 등장하기 전 코드와 호환되게 하도록 남겨둔 것.
``` java
private final Collection machines = ...;

// 실수로 타입이 맞지 않는 객체를 넣을 경우임에도 컴파일되고 런타임 시점에 에러가 발생한다.
machines.add(new Cat(...));
```
이렇게 로 타입은 제네릭 클래스의 안정성과 가독성을 모두 없애버린다. 

``` java
// 제네릭을 활용해 선언했을 경우
private final Collection<Machine> machines = ...;

// 오류 발생!
machines.add(new Cat(...));
```
* 컴파일러가 machines에는 Machine의 인스턴스만 넣어야 함을 인지
* 다른 타입의 인스턴스를 넣으려 하면 컴파일 오류가 발생

## 키 포인트
**List와 같은 로 타입은 사용헤서는 안되나, List< Object>처럼 임의 객체를 허용하는 매개변수화 타입은 괜찮다.**
* List는 제네릭 타입에서 완전히 발을 뺀 것
* List< Object>는 모든 타입을 허용한다는 것을 컴파일러에게 명확히 전달한 것

**실제 타입 매개변수가 무엇인지 신경쓰고 싶지 않다면 와일트카드(List<?>)를 사용하자.**
* 로 타입 컬렉션에는 아무 원소나 넣을 수 있으니 타입 불변식을 훼손하기 쉽다.
* 와일드 카드를 사용하면 null외에는 어떤 원서도 넣을 수 없다.




