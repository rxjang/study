# item27. 비검사 경고를 제거하라
## 비검사 경고
비검사 경고의 예는 다음과 같다. 
``` java
// 형변환 비검사 경고 발생
Set<String> nameSet = new HashSet();

// 타입 매개변수를 명시해 형변환 비검사 경고 제거
Set<String> nameSet = new HashSet<String>();
```
이렇게 비검사 경고를 모두 제거한다면 그 코드는 타입 안정성이 보장돤다. 

## @SupressWarnings
경고를 제거할 수는 없지만 타입이 안전하다고 확신할 수 있을 때 사용해 경고를 숨기는 애노테이션
* @SupressWarnings 는 가능한 좁은 범위에 적용하자.
``` java
@SuppressWarnings("unchecked")
public static <T> T[] toArray(T[] a) {
    return (T[]) Arrays.copyOf(a, a.length, a.getClass());
}

// 한 줄이 넘는 메서드나 생성자에 달린 @SupressWarnings 에너테이션은 지역변수 선언쪽으로 옮기자. 
public static <T> T[] toArray(T[] a) {
    @SuppressWarnings("unchecked")
    T[] result = (T[]) Arrays.copyOf(a, a.length, a.getClass());
    return result;
}
```
* @SupressWarnings("unchecked") 애너테이션을 사용할 때면 그 경고를 무시해도 안전한 이유를 항상 주석으로 남겨야 한다. 