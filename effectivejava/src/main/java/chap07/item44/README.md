# item44. 표준 함수형 인터페이스를 사용하라

## 불필요한 함수형 인터페이스 대신 표준 함수형 인터페이스를 사용하라
* java.util.function 패키지에는 다양한 용도의 표준 함수형 인터페이스가 있다.
* 필요한 용도에 맞는게 있다면, 직접 구현하지 말고 표준 함수형 인터페이스를 활용하라. 
  * API가 다루는 개념의 수가 줄어들어 익히기 더 쉬워진다. 
  * 유용한 디폴트 메서드를 많이 제공하므로 다른 코드와 상호운용성도 크게 좋아진다. 

## 표준 함수형 인터페이스
주요 기본 인터페이스 6가지

| 인터페이스              | 함수 시그니처             | 예                   |
|--------------------|---------------------|---------------------|
| UnaryOperator\<T>  | T apply(T t)        | String::toLowerCase |
| BinaryOperator\<T> | T apply(T t1, T t2) | BigInteger::add     |
| Predicate\<T>      | boolean test(T t)   | Collection::isEmpty |
| Function\<T, R>    | R apply(T t)        | Arrays::asList      |
| Supplier\<T>       | T get()             | Instant::now        |
| Consumer\<T>       | void accept(T t)    | System.out::println | 

* Operator: 반환값과 인수의 타입이 같은 함수
* Predicate: 인수를 하나 받아 boolean 값을 반환
* Function: 인수와 반환 타입이 다른 함수
* Supplier: 인수를 받지 않고 반환 값을 반환
* Consumer: 인수를 하나 받고 반환값은 없는 함수

표준형 함수 인터페이스 대부분은 기본 타입만 지원한다. 그렇다고 **기본 함수형 인터페이스에 박싱된 기본타입을 넣어 사용하지는 말자.**
동작은 하지만 '박싱된 기본타입 대신 기본타입을 사용하라(item 61)'조언을 위배하고, 계산량이 많을 때는 성능이 느려질 수 있다. 

## 표준 함수형 인터페이스 대신 직접 작성해야 할 때
1. 자주 쓰이며 이름이 용도를 명확히 설명해주는 경우
2. 구현하는 쪽에서 반드시 지켜야할 규칙을 담고 있는 경우
3. 비교자들을 변환하고 조합해주는 디폴트 메서드들을 담고 있는 경우

## @FunctionalInterface
직접 만든 함수형 인터페이스에는 @FunctionalInterface를 사용하자.
1. 해당 클래스의 코드나 설명 문서를 읽는 이에게 그 인터페이스가 람다용으로 설계된 것임을 알려준다.
2. 해당 인터페이스가 추상 메서드를 오직 하나만 가지고 있어야 컴파일되게 해준다.
3. 유지보수 과정에서 누군가 실수로 메서드를 추가하지 못하게 막아준다. 

## 함수형 인터페이스 사용 주의점
서로 다른 함수형 인터페이스를 같은 위치의 인수로 받는 메서드들을 다중정의해서는 안된다. 