# item62. 다른 타입이 적절하다면 문자열 사용을 피하라
문자열을 쓰지 않아야 할 시례를 알아보자 
## 문자열은 다른 값 타입을 대신하기에 적합하지 않다
파일, 네트워크, 키보드 입력으로 데이터를 받을 때 문자열을 사용하는 경우가 많다.
하지만 이는 좋지 않는 방식이고, 입력 받을 데이터가 진짜 문자열일 때만 문자열을 사용하는 것이 좋다. 
**기본 타입이든 참조 타입이든 적절한 값 타입이 있다면 그것을 사용하고 없다면 새로 작성하자.**

## 문자열은 열거 타입을 대신하기에 적합하지 않다.

## 문자열은 혼합 타입을 대신하기에 적합하지 않다.
``` java
String compundKey = className + "#" + i.next();
```
위는 단점이 많은 방식이다. 두 요소중 하나에 #이 추가로 들어가면 혼란스러운 결과를 초래하고, 개별 요소를 파싱해야 해서 느리고 귀찮으며 오류 가능성이 커진다. 
이런경우 차라리 전용 클래스를 새로 만드는 편이 낫다.

## 문자열은 권한을 표현하기에 적합하지 않다.
예를 들어 스레드 지역변수 기능을 설계한다고 해보자. 
```java
public class ThreadLocal {
    private ThreadLocal() {} // 객체 생성 불가
    
    // 현 스레드의 값을 키로 구분해 저장
    public static void set(String key, Object value);
    
    // 키다 기리키는 현 스레드의 값을 반환
    public static Object get(String Key);
}
```
이 방식은 스레드 구분용 문자열 키가 전역 이름 공간에서 공유된다는 점이 문제다. 
만약 두 클라이언트가 서로 소통하지 못해 같은 키를 쓰기로 결정한다면 의도치 않게 같은 변수를 공유하게 된다. 
```java
public class ThreadLocal() {
    private ThreadLocal() {}
    
    public static class Key {
        Key() {}
    }
    
    public static Key getKey() {
        return new Key();
    }
    
    public static void set(Key key, Object value);
    public static Object get(Key key);
}
```
위처럼 문자열 대신 Key 클래스를 만들어 권한을 구분했다. 