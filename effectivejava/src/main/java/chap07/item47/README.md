# item47. 반환 타입으로는 스트림보다는 컬렉션이 낫다.
* 자바 7까지는 Collection, Set, List 같은 컬렉션 인터페이스, 혹은 Iterable이나 배열울 알렬의 원소를 반환하는데 사용했다.
* 기본은 컬렉션 인터페이스이며, for-each 문에서만 쓰이거나 반환된 원소 시퀀스가 일부 Collection 메서드를 구현할 수 없을 때는 Iterable 인터페이스를 사용했음


## 반복 
* 스트림은 반복(iteration)을 지원하지 않으므로 스트림과 반복을 알맞게 조합해야 좋은 코드가 나옴
* Stream 인터페이스는 Iterable 인터페이스가 정의한 추상 메서드를 전부 포함하고 Iterable 인터페이스가 정의한 방식대로 동작함 
  * for-each로 반복할 수 없는 이유는 Stream이 Iterable을 상속하지 않았기 때문

``` java
// 자바 타입 추론의 한계로 컴파일 되지 않음
for (ProcessHandle ph : ProcessHandle.allProcesses()::iterator) { // 컴파일 에러 발생
    // 프로세스를 처리한다.
}
```
``` java
// 스트림을 반복하기 위한 좋지 않은 우회 방법
for (ProcessHandle ph : (Iterable<ProcessHandle>) ProcessHandle.allProcesses()::iterator) {
    // 프로세스를 처리한다.
}
```
``` java
for (ProcessHandle p : iterableOf(ProcessHandle.allProcesses())) {
    // 프로세스를 처리한다.
}

...

public static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return stream::iterator;
}
```
* 어댑터 메서드를 사용하면 자바의 타입 추론이 문맥을 파악해 메서드 안에서 따로 형변환하지 않아도 됨
* 어댐터를 사용하면 어떤 스트림도 for-each문으로 반복 가능

``` java
// Iterable<E>를 Stream<E>로 중개해주는 어댑터
public static <E> Stream<E> streamOf(Iterable<E> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
}
```

## 스트림을 반환하는 방법
```java
// 방법 1
public class SubLists {
    public static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(Stream.of(Collections.emptyList()), prefixes(list).flatMap(SubLists::suffixes));
    }

    private static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size())
                .mapToObj(end -> list.subList(0, end));
    }

    private static <E> Stream<List<E>> suffixes(List<E> list) {
        return IntStream.range(0, list.size())
                .mapToObj(start -> list.subList(start, list.size()));
    }
}
```
```java
// 방법2
public class SubLists {
    public static <E> Stream<List<E>> of(List<E> list) {
        return IntStream.range(0, list.size())
                .mapToObj(start -> IntStream.rangeClosed(start + 1, list.size())
                        .mapToObj(end -> list.subList(start, end)))
                .flatMap(x -> x);

    }
}
```

## 핵심 정리
* 원소 시퀀스를 반환하는 메서드를 작성할 때는, 스트림과 반복에서의 사용을 모두 고려해야 한다. 
* 컬렉션을 반환할 수 있다면 컬렉션을 반환하도록 한다. 
* 반환 전부터 이미 원소들을 컬렉션에 담아 관리하고 있거나 컬렉션을 하나 더 만들어도 될 정도로 원소 개수가 적다면 ArrayList 같은 표준 컬렉션에 담아 반환하도록 한다. 
  * 만약 그렇지 않다면 전용 컬렉션을 구현할지 고민해보도록 한다.
