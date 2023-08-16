# item46. 스트림에서는 부작용 없는 함수를 사용하라

## 스트림
* 함수형 프로그래밍에 기초한 패러다임으로 볼 수 있음
* 스트림 패러다임의 핵심은 계산을 일련의 변환으로 재구성 하는 부분
* 각 변환 단계는 가능한 이전 단계의 결과를 받아 처리하는 순수함수여야함
  * 순수함수란? 오직 입력만이 결과에 영향을 주는 함수
* 즉, 스트림 연산에 건네는 함수 객체는 모두 부작용이 없어야 함

``` java
// 스트림 패러다임을 이해하지 못하고 API만 사용한 경우
Map<String, Long> freq = new HashMap<>();
try (Stream<String> words = new Scanner(file).tokens()) {
    words.forEach(word -> {
        freq.merge(word.toLowerCase(), 1L, Long::sum);
    });
}
```
* 스트림 코드를 가장한 반복적 코드
* 모든 작업은 종단 연산인 forEach에서 일어나는대 이 때 외부 상태(빈도표)를 수정하는 람다를 실행하면서 문제가 생김

``` java
// 스트림을 제대로 활용해 빈도표를 초기화함
Map<String, Long> freq;
try (Stream<String> words = new Scanner(file).tokens()) {
    freq = words.collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
}
```
* for-each 반복문은 종단 연산과 비슷하게 생겼으나, forEach 연산은 종단 연산 중 기능이 가장 적고 대놓고 반복적이라 병렬화 할 수 없다.
* **forEach연산은 스트림 계산결과를 보고할 때만 사용하고, 계산하는데는 쓰지 말도록 하자.**

## Collectors
`java.util.stream.Collectors` 클래스는 스트림을 사용하려면 꼭 배워야 하는 개념으로, 스트림의 원소들을 객체 하나에 취합하는 여러 메서드를 제공해준다.
* 39개의 메서드를 가지고 있으며, 그 중에 몇가지를 알아보자. 

### toList(), toSet(), toCollection(collectionFactory)
수집기를 사용하면 스트림의 원소를 손쉽게 컬렉션으로 모을 수 있다.
``` java
// 빈도표에서 가장 흔한 단어 10개를 뽑아내는 파이프라인
List<String> topTen = freq.keySet().stream()
    .sorted(Comparator.comparing(freq::get).reversed())
    .limit(10)
    .collect(Collectors.toList());
```

### toMap
* 간단한 맵 수집기
``` java
private static final Map<String, Operation> stringToEnum = 
    Stream.of(values()).collect(
    toMap(Object::toString, e -> e));
```

### groupingBy
입력으로 분류 함수를 받고 출력으로는 원소들을 카테고리별로 모아 놓은 맵을 담은 수집기를 반환하는 메서드
``` java
words.collect(groupingBy(word -> alphabetize(wrod)))
```

### partitioningBy
분류험수 자리에 predicate를 받고 키가 boolean인 맵을 반환

### counting, summing, averaging...
### minBy, maxBy
인수로 받은 비교자를 이용해 스트림에서 값이 가장 작은 혹은 가장 큰 우너소를 찾아 반환한다. 

### joining
문자열 등의 charSequence에만 적용 가능
* 매개변수가 없는 joining은 단순히 원소들을 연결하는 수집기를 반환