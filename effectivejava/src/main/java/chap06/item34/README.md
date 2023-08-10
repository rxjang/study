# item34. int 상수 대신 열거 타입을 사용하라
## 열거타입
일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입  

**과거 열거 패턴 사용 방식 (정수형)**
``` java
public static final int APPLE_FUJI = 0;
public static final int APPLE_PIPPIN = 1;
public static final int APPLE_GRANNY_SMITH = 2;

public static final int ORANGE_NAVEL = 0;
public static final int ORANGE_TEMPLE = 1;
public static final int ORANGE_BLOOD = 2;
```
정수형 열거 패턴은 타입 안전을 보장할 방법이 없으며 표현력도 좋지 않다. 
``` java
// 오렌지를 건내야할 메서드에 사과를 건내도 아무런 경고메시지가 출력되지 않는다.
int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;
```
이렇게 정수형 열거 패턴을 사용한 프로그램은 깨지기 쉽다. 정수 상수는 값을 출력하거나 디버거로 살펴보면 단지 숫자로 보여 도움도 되지 않는다.
이는 자바가 제공하는 열거타입을 통해 해결할 수 있다.

## 열거 타입(enum type)
```java
public enum Apple { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }
```