# item42. 익명 클래스보다는 람다를 사용하라
## 함수 객체
* 함수 타입을 표현할 때 쓰던 추상 메서드 하나만 담은 인터페이스
* 특정 함수나 동작을 나타내는데 쓰임

``` java
// 익명 클래스 방식 - 예전 기법
Collections.sort(words, new Comparator<String>() {
    public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
    }
});
```
과거 객체지향 디자인 패턴에는 위처럼 익명 클래스면 충분했다. 하지만 이 방식은 코드가 너무 길기 때문에 함수형 프로그래밍에는 적합하지 않다.

## 람다
자바 8이후부터는 인터페이스 내 추상 메서드가 단 하나만 존재하는 람다식을 사용할 수 있게됨
* 함수를 하나의 식으로 표현한 것
* 익명 함수의 한 종류 -> 메소드의 이름이 필요 없기 때문
``` java
// 익명 클래스 방식을 아래와 같이 람다식으로 간단히 변경 가능
Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
```
코드에는 반환값 타입의 언급이없다. 컴파일러가 문맥을 살펴 타입을 추론해준다. **타입을 명시해야 코드가 더 명확할 때만 제외하고는, 람다의 모튼 매개변수 타입은 생략하자.**
``` java
// 더 간결하게 만들 수 있다
Collections.sort(words, Comparator.comparingInt(String::length));
words.sort(Comparator.comparingInt(String::length));
```

### 람다를 쓰지 말아야 할 때
* 코드 자체로 동작이 명확히 설명되지 않을 때
* 코드 줄 수가 많아질 때

### 람다로 대체 할 수 없는 곳
* 추상 클래스의 인스턴스를 만들 때 람다를 쓸 수 없으므로 익명 클래스를 써야함
* 추상 메서드가 여러개인 인터페이스의 인스턴스를 만들 때
* 자기 자신을 참조 하려고 시도할 때

### 주의사항
람다도 익명 클래스처럼 직렬화 형태가 구현별로 다를 수 있다. **따라서 람다를 직렬화 하는 일은 극히 삼가야 한다.**