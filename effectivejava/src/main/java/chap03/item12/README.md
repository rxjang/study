# item12. toString을 항상 재정의하라
toString의 일반 규약읜 다음과 같다.
1. 간결하면서 사람이 읽기 쉬운 형태의 유익한 정보를 반환하라.
2. 모든 하위 클래스에서 이 메서드를 재정의하라.

하지만 Java에서 기본적으로 구현되어 있는 toString은 `클래스명@해시코드값`울 반환해 이 규약과 걸맞지 않는다. 
``` java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```
toString()은 주로 콘솔로 확인하거다, 디버깅, 로깅할 때 사용한다. 이 주체는 모두 개발자로, 위와 같이 정의된 정보로는 객체 안의 내용을 확인할 수 없다.
그러므로 규약2에 맞게 재정의해서 유익한 정보를 전달할 수 있도록 해야한다.

## toString 재정의
* 객체 스스로를 완벽히 설명하는 문자열이어야한다.
* 객체가 가진 주요 정보를 모두 반환하는 것이 좋다. 
* 문서화를 권장하기도 한다.
``` java
/** 
 * 전화번호의 문자열 표현을 반환한다.
 * 이 문자열은 XXX-YYYY-ZZZZ 형태의 11글자로 구성된다.
 * XXX는 지역코드, YYYY는 접두사, ZZZZ는 가입자 번호이다.
 * 각각의 대문자는 10진수 숫자 하나를 나타낸다. 
*/
@Override
public String toString() {
    return String.format("%03d-%04d-%04d", areaCode, prefix, lineNum);
}
```