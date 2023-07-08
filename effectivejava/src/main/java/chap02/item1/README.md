# 아이템1. 생성자 대신 정적 팩터리 메서드를 고려하라
`eg`
``` java
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

## 정적 팩토리 매서드가 생성자보다 좋은 다섯가지 이유
1. 이름을 가질 수 있다.
* `BigInteger(int, int, Random)` VS `BigInteger.probablePrime` 중 어느 쪽이 '값이 소수인 BigInteger을 반환 한다' 는 의미를 더 잘 내포하고 있는가?
* 하나의 시그니처로는 생성자를 하나만 만들 수 있다. 정적 팩토리 메서드에는 이런 제약이 없다.

2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
* `Boolean.valudOf(boolean)` 메서드는 객체를 아예 생성하지 x -> 생성 비용이 큰 같은 객체가 자주 요청 되는 상황이라면 성능을 끌어올려줌.

3. 반환 타입의 하위 타입 객체를 반환 할 수 있는 능력이 있다.  
   클래스의 생성자는 해당 클래스의 인스턴스만 만들 수 있다. 하지만 정적 팩토리 매소드를 사용하면 하위 클래스의 인스턴스까지 반환 할 수 있다.
   새로운 인터페이스와 수많은 구현클래스가 있을 때, 구현 클래스의 생성자로 인스턴스를 만드는 게 아니라 인터페이스의 정적 팩토리 메소드로 인스턴스를 만들어서 개발자가 수많은 구현 클래스들을 이해하지 않고도 인터페이스를 사용할 수 있도록 할 수 있다.
``` java
List<Intger> list = List.of(1, 2, 3, 4, 5);
```

4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.  
   같은 메서드라도 상황에 따라 다른 클래스 인스턴스를 반환 할 수 있다.
``` java
// EnumSet은 상황에 따라 두 개의 클래스 인스턴스 중 하나를 반환한다.
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
    Enum<?>[] universe = getUniverse(elementType);
    if (universe == null)
        throw new ClassCastException(elementType + " not an enum");

    if (universe.length <= 64)
        return new RegularEnumSet<>(elementType, universe);
    else
        return new JumboEnumSet<>(elementType, universe);
}
```
5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
   메소드와 반환 타입을 선언해 놓고 실제 반환 타입은 나중에 구현 가능하다. 대표적인 옗는 JDBC가 있다.

## 정적 팩토리 메서드의 단점
1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.  
   하지만 이 제약은 상속보다 컴포지션을 사용하도록 유도하고 불변 타입을 만들려면 이 제약을 지켜야 하므로 장점으로 받아 질 수도 있다.
2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.
   그래서 흔히 사용하는 명명 방식들이 존재한다.
``` java
// from: 매게변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드
Date d = Date.from(instant);
// of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집게 메서드
Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
// valueOf: from 과 of의 더 자세한 버전
BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
// instacne or getInstance: 매개변수로 명시한 인스턴스를 반환하지만 같은 인스턴스임을 보장하지 않는다. 
StackWalker luke = StackWalker.getInstance(options);
// create or newInstance: instance, getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장
Object newArray = Array.newInstance(classObject, arrayLen);
// getType: getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 씀
FileStore fs = Files.getFileStore(path)
// newType: newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 씀.
BufferedReader br = Files.newBufferedReader(path);
// type: getType과 newType의 간결한 버전
List<Compliant> litany = Collections.list(legacyLitany);
```
---