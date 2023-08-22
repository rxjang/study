# item53. 가변인수는 신중히 사용하라

## 가변인수
명시한 타입의 인수를 0개 이상 받을 수 있는 변수
* 가변인수 메서드 호출시, 인수의 개수와 길이가 같은 배열이 만들어지고, 인수들은 이 배열에 저장되어 가변인수 메서드에 건내준다.

**가변인수 예시**
``` java
// 잘못 구현한 예
static int min(int ...args) {
    if (args.length == 0) {
        throw new IllegalArgumentException("인수가 1개이상 필요합니다.");
    }
    int min = args[0];
    for (int i = 1; i < args.length; i++) {
        if (args[i] < min) min = args[i];
    }
    return min;
}
```
위 방식에는 인수를 0개 넣어서 호출을 하면 컴파일 타임이 아닌 런타임에 실패한다는 문제점이 있다. 위 예시는 아래처럼 바꾸면 낫다.
``` java
static int min(int firstArg, int ...remainingArgs) {
    int min = firstArg;
    for (int arg : remainingArgs) {
        if (arg < min) min = arg;
    }
    return min;
}
```
위에서 알 수 있듯 가변인수는 개수가 정해지지 ㅇ낳았을 때 아주 유횽하다.

## 가변인수의 성능
성능에 민감한 상황이라면 가변인수는 걸림돌이 될 수 있다. 
가변인수 메서드는 호출될 때마다 배열을 새로 하나 할당하고 초기화하기 때문이다. 다행히 이는 우회하는 방법이 존재한다.  

예를 들어 해당 메서드 호출의 95%가 인수를 3개 이하로 사용한다고 하면 아래와 같은 방법이 비교적 효과적일 것이다. 
``` java
public void foo() { }
public void foo(int a1) { }
public void foo(int a1, int a2) { }
public void foo(int a1, int a2, int a3) { }
public void foo(int a1, int a2, int a3, int... rest) { }
```
EnumSet의 정적 팩터리도 이 기법을 사용해 열거 타입 집합 생성 비용을 최소화한다.
