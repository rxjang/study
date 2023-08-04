# item30. 이왕이면 제네릭 메서드로 만들라

매개변수화 타입을 받는 정적 유틸리티 메서드는 보통 제네릭이다.

eg) Collections의 binaySearch

``` java
public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
    if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
        return Collections.indexedBinarySearch(list, key);
    else
        return Collections.iteratorBinarySearch(list, key);
}
```

## 제네릭 메서드 작성법

``` java
// 로 타입 사용
public static Set unioin(Set s1, Set s2) {
	Set result = new HashSet(s1);
	result.addAll(s2);
	return result;
}

// 제네릭 메서드
public static <E> Set<E> unioin(Set<E> s1, Set<E> s2) {
	Set<E> result = new HashSet<>(s1);
	result.addAll(s2);
	return result;
}
```

- (타입 매개변수들을 선언하는) 타입 매개변수 목록은 메서드의 제한자와 반환 타입 사이에 온다.

## 제네릭 싱글턴 팩터리

불변 객체를 여러 타입으로 활용할 수 있게 만들어야 할 때 사용.

- 제네릭은 런타임에 타입 정보가 소거되므로 하나의 객체를 어떤 타입으로든 매개변수화 할 수 있다.
- 하지만, 요청한 타입 매개변수에 맞게 매번 그 객체의 타입을 바꿔주는 **정적 팩터리**를 생성했을 때만 가능하다. → 이러한 패턴을 **제네릭 싱글턴 팩터리** 라고 함
- eg) Collections.reverseOrder

### 항등함수를 통한 제네릭 싱글턴 펙터리 예시

``` java
private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;

@SuppressWarnings("unchecked")
public static <T> UnaryOperator<T> identityFunction() {
	return (UnaryOperator<T>) IDENTITY_FN;
}
```

- IDENTITY_FN 을 UnaryOperator<T>로 형변환하면 비검사 형변환 경고가 발생한다.
- 하지만 항등함수는 입력값을 수정없이 그대로 반환하므로, T가 어떤 타입이든 UnaryOperator<T> 를 사용해도 안전하다.

## 재귀적 타입 한정

재귀적 타입 한정이란, **자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정**하는 것이다.

```java
public interface Comparable<T> {
	int compareTo(T o);
}
```

- Comparable을 구현한 원소의 컬렉션을 입력받는 메서드들은 주로 그 원소들을 정렬, 혹은 검색하거나 최솟값, 최댓값을 구하는 식으로 사용됨
- 이 제약을 재귀적 타입한정으로 표현하면 다음과 같다.

``` java
public static <E extends Comparable<E>> E max(Collection<E> c);
```

- 타입 한정인 <E extends Comparable<E>> 는 **모든 타입 E는 자신과 비교할 수 있다**라는 뜻이다.