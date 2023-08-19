# item50. 적시에 방어적 복사본을 만들라
## 자바는 안전한 언어다.
자바는 네이티브 메서드를 사용하지 않아 C, C++ 같은 언어에서 보이는 버퍼 어버런, 배열 오버런, 와일드 포인터 가은 메모리 충동 오류에서 안전하다. 
허지만 자바라 하더라도 다른 클래스로부터의 침범을 받아 불변식을 깨드릴 수 있다. 

## 불변식을 지키지 못한 Period 에시
``` java
public final class Period {
    private final Date start;
    private final Date end;
    
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(
                    start + " after " + end);
        this.start = start;
        this.end   = end;
    }

    public Date start() {
        return start;
    }
    public Date end() {
        return end;
    }
}

// Period 인스턴스의 내부를 공격하는 코드 
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
end.setYear(78); // P의 값이 바뀜
```
위 Period클래스는 Date가 가변이라는 사실을 이용해 불변식을 깨뜨렸다. 
**외부 공격으로 부터 인스턴스의 내부를 보호하려면 생성자에서 받은 가변 매개변수 각각을 방어적으로 복사(defensive copy)해야 한다.**

## 불변 클래스 Period 예시
``` java
public final class Period {
    private final Date start;
    private final Date end;
    
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end   = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(
                    this.start + " after " + this.end);
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
```
이제 Period는 불변 클래스가 되었다.  
**매개변수의 유효성을 검사(item49)하기 전에 방어적 복사본을 만들고, 이 복사본으로 유효성을 검사한 점에 주목하자.** 
멀티 스레딩 환경이라면 원본 객체의 유효성을 검사한 후 복사본을 만드는 그 찰나의 취약한 순간에 다른 스레드가 원본 객체를 수정할 위험이 있다.
방아적 복사를 매개변수 유효성 감서전에 수행하면 이런 위험에서 해방될 수 있다. 

``` java
// Period를 향한 두번째 공격
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
p.end().setYear(78);
```
전의 코드였으면, 이 공격으로 인해 end의 값이 변경되었을 것이다. 하지만 가변 필드의 방어적 복사본을 반환해 이 공격도 막아내었다. 

## 방어적 복사
* 단순히 불변 객체를 만들기 위해서만은 아님
* 방어적 복사에는 성능 저하가 따르고 항상 쓸 수 있는 것도 아니다.
* 호출자가 컴포넌트 내부를 수정하지 않으리라 확신하면 방어적 복사를 생략할 수 있다. 

