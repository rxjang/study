# item61. 박싱된 기본 타입보다는 기본 타입을 사용하라

## 자바의 데이터 타입
* 기본타입: int, double, boolean
* 참조타입: String, List, Integer, Double, Boolean

참조 타입에서 Integer, Double, Boolean은 각각 int, double, boolean에 대응되며 `박싱된 기본 타입`이라고 한다. 

## 기본 타입과 박싱된 기본 타입의 차이
### 기본 타입은 값만 가지고 있으나, 박싱된 기본타입은 식별성을 추가로 가진다.
박싱된 기본 타입의 두 인스턴스는 값이 같아도 서로 다르다고 식별될 수 있다.
``` java
Comparator<Integer> natureOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);

// 0을 출력할것 같지만 실제로 1을 출력
int compare = natureOrder.compare(new Integer(42), new Integer(42));
System.out.println(compare);
```
위 코드는 일반 테스트시에는 잘 돌아간다. 하지만 위처럼 새로운 Integer을 생성하고 테스트를 하면 예상과 다른 결과값을 출력한다.
두번째 비교에서 (i == j) 두 객체의 참조 식별성을 검사하게되 비교의 결과가 false가되고 1을 반환하게 된다. 
이처럼 **박싱된 기본 타입에 == 연산자를 사용하면 오류가 일어난다.** 

### 기본 타입의 값은 null을 가질 수 없으나, 박싱된 기본 타입은 null을 가질 수 있다.
``` java
static Integer i;

public static void main(String[] args) {
    // NullPointerException 발생
    if (i == 42) {
        System.out.println("???");
    }
}
```
기본 타입과 박싱된 기본 타입을 혼용한 연산에서는 박싱된 기본 타입의 박싱이 자동으로 풀린다. 
그리고 null 참조를 언박싱해 NullPointerException이 발생한다.

### 기본타입이 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적이다.
``` java
public static void main(String[] args) {
Long sum = 0L;
    for (long i = 0; i <= Integer.MAX_VALUE; i++) {
        sum += i;
    }
    System.out.println(sum);
}
```
이 프로그램은 지역변수 sum을 박싱된 기본 타입으로 선언해 느려졌다. 박싱과 언박싱이 반복해서 일어나 체감될 정도로 성능이 느려졌다.

## 박싱된 기본 타입을 써야할 때
* 컬렉션의 원소, 키, 값으로 쓸 때 
  * 컬렉션은 기본 타입을 담을 수 없으므로 어쩔 수 없이 박싱된 기본타입을 써야만 한다.
* 리플렉션을 통해 메서드를 호출할 때