# item58. 전통적인 for 문보다는 for-each문을 사용하라
## 전통적인 for문의 단점

``` java
// 전통적인 for문
for (Iterator<Elemet> i = c.iterator() ; i.hasNext();) {
    Element e = i. next();
    ...
}
```

* 반복자와 인덱스 변수 코드를 지저분하게 한다.
* 쓰이는 요소 종류가 늘어나면 오류가 생길 가능성이 높아진다.
* 잘못된 변수를 사용했을 때 컴파일러가 잡아주리라는 보장이 없다.
* 컬렉션이나 배열이냐에 따라 코드 형태가 달라지므로 주의해야한다.

이 문제들은 **향상된 for문**을 통해 해결할 수 있다.

## 향상된 for 문
``` java
// 향상된 for문 예시
for (Elenet e: elements) {
    // e로 무언가를 한다.
}
```
향상된 for문을 사용하면 이처럼 코드가 깔끔해진다.

## 향상된 for문을 사용할 수 없는 세가지 상황
### 파괴적인 필터링
컬렉션을 순회하면서 선택된 원소를 제거해야한다면 반복자의 remove메서드를 호출해야한다.
### 변형
리스트나 배열을 순회하면서 그 원소 값 일부 혹은 전체를 교체해야 한다면 리스트의 반복자나 배열의 인덱스를 사용해야 한다. 
### 병령 반복
여러 컬렉션을 순회해야한다면 가각의 반복자와 인덱스 변수를 사용해 엄격하고 명시적으로 제어해야 한다. 

## Iterable 객체 구현하기
for-each문은 컬렉션과 배열뿐만 아니라 `Iterable` 인터페이스를 구현한 객체라면 무엇이든 순회할 수 있다.
```java
public interface Iterable<E> {
    // 이 객체의 원소들을 순회하는 반복자들을 반환한다.
    Iterator<E> iterator();
}
```
