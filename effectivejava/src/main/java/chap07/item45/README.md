# item45. 스트림은 주의해서 사용하라

## 스트림
다량의 데이터 처리 작업을 돕고자 자바 8에 추가됨
스트림이 제공하는 2가지 핵심 추상 개념
1. `스트림`은 데이터 원소의 유한 혹은 무한 시퀀스를 뜻함
2. `스트림 파이프라인`은 이 원소들로 수행하는 연산 단계를 표한하는 개념

## 스트림 파이프라인
소스 스트림 -> (하나 이상의) 중간 연산 -> 종단 연산

### 지연 평가 (Lazy evaluation)
평가는 종단 연산이 호출될 때 이뤄지며, 종단 연산에 쓰이지 않는 데이터 원소는 계산에 쓰이지 않는다. 
이런 지연평가는 무한스트림을 다루는 열쇠다. 종단 연산이 없는 스트림 파이프라인은 아무 일도 하지 않으므로 **종단 연산을 빼먹는 일이 절대 없도록 하자**

### 플루언트 API
* 파이프라인 하나를 구성하는 모든 호출을 연결해 단 하나의 표현식으로 완성할 수 있다. 
* 기본적으로 스트림파이프라인은 순차적으로 수행된다.
* parallel 메서드를 호출해 병렬로 수행할 수 있으나, 효과를 볼 수 있는 상황은 많지 않다.

## 스트림을 언제 써야하는가
기존 코드는 스트림을 사용하도록 리펙터링하되, 새 코드가 더 나아보일 때만 반영하자.  

## 함수 객체(스트림) vs 코드 블록(반복문)
### 함수객체로는 불가능 하지만 코드 블록으로 할수 있는 일
* 범위 안의 지역 변수를 읽고 수정할 수 있음
* break, continue를 활용해 반복문을 종료, 뛰어넘을 수 있음
* 메서드 선언에 예외 처리가 가능함 


### 스트림 사용을 추천하는 경우
* 원소들의 시퀀스를 일관되게 반환한다.
* 원소들의 시퀀스를 필터링한다.
* 원소들의 시퀀스를 하나의 연산을 사용해 결합한다.
* 원소들의 시퀀스 컬렉션에 모은다.
* 원소들의 시퀀스에 특정 조건을 만족하는 원소를 찾는다. 

## 가독성과 유지보수가 더 나은 쪽을 택하자.

### Anagram 예시
```java
// 코드블록을 사용
public class Anagrams {
    public static void main(String[] args) throws IOException {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);
        Map<String, Set<String>> groups = new HashMap<>();

        try(Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>())
                        .add(word);
            }

            for (Set<String> group: groups.values()) {
                if (group.size() >= minGroupSize)
                    System.out.println(group.size() + ": " + group);
             }
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
```
```java
// 스트림을 사용한 경우
public class Anagrams {
    public static void main(String[] args) throws IOException {
        File dectionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        try (Stream<String> words = Files.lines(dectionary.toPath())) {
            words.collect(groupingBy(word -> alphabetize(word)))
                    .values().stream()
                    .filter(group -> group.size() >= minGroupSize)
                    .forEach(group -> System.out.println(group.size() + ": " + group));
        }
    }
}
```
-> 스트림 쪽이 가독성이 좋아 보인다.

### 데카르트 곱 예시

``` java
// 반복문 사용
private static List<Card> newDeck() {
    List<Card> result = new ArrayList<>();
    for(Suit suit : Suit.values()) 
        for(Rank rank : Rank.values())
            result.add(new Card(suit, rank));
    return result;
}
```

``` java
// 스트림 사용
private static List<Card> newDeck() {
    return Stream.of(Suit.values())
    .flatMap(suit -> Stream.of(Rank.values())
                      .map(rank -> new Card(suit, rank)))
    .collect(toList());
}
```
-> 반복문 쪽이 가독성이 좋다. 