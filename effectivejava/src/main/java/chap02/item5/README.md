# item5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다.
이 자원들은 클래스가 직접 만들게 해서도 안 된다. 대신 필요한 자원을 생성자에 넘겨주자. 의존 객체 주입이라 하는 이 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.

유연하지 않고 테스트하기 어려운 예
``` java
// 정적 유틸리티 잘못 사용 예
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker() {} // 객체 생성 방지
}

// 싱글턴을 잘못 사용한 예
public class SpellChecker {
    private static final Lexicon dictionary = ...;
    private SpellChecker(...) {}
    public static SpellChecker INSTANCE = new SpellChecker(...);
}

```

의존 객체 주입으로 유연성과 테스트 용이성을 높여준 예

``` java
public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }
}
```

## 의존 객체가 불변이라면, 여러 클라이언트가 의존 객체들을 공유할 수 있다?
* 인스턴스 변수로 선언된 의존 객체(`private final Lexicon dictionay;`)는 메모리의 Heap 영역에 생성됨.
* 인스턴스 변수이므로 `SpellChecker`의 객체를 생성할 떄마다 새로 생성됨.
* '여러 클라이언트' -> '여러 요청이 들어온다' 일 때, 여러 요청마다 쓰레드가 생김
* 쓰레드는 각자의 Stack이 생기며, 메모리의 Heap, Method Area영역은 공유됨.
* Heap영역은 공유되므로 여러 개의 쓰레드는 인스턴스 변수로 선언된 의존 객체에 접근할 수 있음
* 의존 객체가 불변이 아닐 시 -> 스레드는 의존 객체에 접근해 상태를 바꿀 수 있음
* 의존 객체가 불변일시 -> 스레드는 의존 객체에 접근해서 필드값을 바꿀 수 없음