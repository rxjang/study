# item49. 매개변수가 유효한지 검사하라

## 매개변수의 제약사항은 메서드 몸체가 시작되기전에 검사하자
매서드 몸체가 실행되기 전에 매개변수를 확인한다면 잘못된 값이 넘어왔을 때 즉각적이고 깔끔한 방식으로 예외를 던질 수 있다. 
이는 "오류는 가능한 빨리 (발생한 곳에서) 잡아야한다"는 일반 원칙의 한 사례이기도 하다. 

## 매개변수 검사를 하지 못했을 때 생기는 문제점
* 메서드가 수행되는 중간에 모호한 예외를 던지며 실패할 수 있다.
* 메서드가 잘 수행되지만 잘 못된 결과를 반환할 수 있다.
* 메서드는 문제 없이 수행됐디만, 어떤 객체를 이상한 상태로 만들어 놓아 미래의 알 수 없는 시점에 이 메서드와 관련없는 오류를 낼 수 있다.

## 예외 문서화
``` java
/**
 * Returns a BigInteger whose value is {@code (this mod m}).  This method
 * differs from {@code remainder} in that it always returns a
 * <i>non-negative</i> BigInteger.
 *
 * @param  m the modulus.
 * @return {@code this mod m}
 * @throws ArithmeticException {@code m} &le; 0
 * @see    #remainder
 */
public BigInteger mod(BigInteger m) {
    if (m.signum <= 0)
        throw new ArithmeticException("BigInteger: modulus not positive");

    BigInteger result = this.remainder(m);
    return (result.signum >= 0 ? result : result.add(m));
}
```
* 외부에서 사용 가능한 문서화는 메서드는 매개변수 값이 잘못됐을 때 던지는 예외를 문서화해야한다. 

## Object.requireNonNull
* `java.util.Objects.requireNonNull` 메서드는 유연하고 사용하기도 편하니, 더 이상 null 검사를 수동으로 하지 않아도 된다.
* 원하는 예외 메세지를 지정할 수 있다. 
``` java
this.strategy = Objects.requireNonNull(strategy, "전략");
```

## assert
내부에서만 사용하는 메서드라면 해당 메서드가 호출되는 상황을 통제할 수 있다. 즉, 오직 유효한 값만에 메서드에 넘겨지라는 것을 보증할 수 있다. 
이 경우, assert를 사용해 유효성 검사를 할 수 있다. 
``` java
private static void sort(long a[], int offset, int length) {
    assert a != null;
    assert offset >= 0 && offset <= a.length;
    assert length >= 0 && length <= a.length - offset;
    ...
}
```
assert문은 true/false로 판단 가능한 조건을 명시적으로 선언하고, java 실행시 명령어 (`-ea` or `--enableassertions`)를 통해 런타임에도 유효성 검사를 수행한다.
하지만 이 명령어가 없는 일반적인 실행환경에서는 아무런 동작도 하지 않는다.

## 예외 사항
유효성 감사 비용이 지나치게 높거나 실용적이지 않을 때, 계산 과정에서 암묵적으로 검사가 수행될 때는 위 규칙도 예외가 된다. 