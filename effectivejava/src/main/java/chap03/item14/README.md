# item14. Comparable을 구현할지 고려하라. 
Comparable 인터페이스는 compareTo 메소드를 가지고 있다. 이는 두 객체를 비교해 그 결과를 반환한다.
equals()와 비슷해 보이지만, 이는 두 객체의 논리적 동일성을 판단하고, compareTo()는 두 객체의 선후관계를 비교해 정렬에 사용된다. 

## compareTo 일반 규약
compareTo는 선후관계를 나타내기 때문에 -1, 0, 1을 반환한다.  
sgn(표현식)은 수학에서 말하는 부호함수를 뜻한다.
* sgn(x.compareTo(y)) = -sgn(y.compareTo(x))
* 추이성을 보장해야 한다. 즉, x.compareTo(y) > 0 이고 y.compareTo(z) 이면, x.compareTo(z) > 0 이다.
* x.compareTo(y) == 0 이면, x.compareTo(z) == y.compareTo(z) 이다.
* 권고사항:  x.compareTo(y) == 0 이면, x.equals(y) == true 이다.  

compareTo도 equals와 마찬가지로 반사성, 대칭성, 추이성을 충족해야한다.  

---

* compareTo()에서 관계얀산자 `<`와 `>`를 사용하는 방식은 거추장스럽고 오류를 유발하니 추천하지 않는다.
* 클래스에 핵심 필드가 여러개라면 어느것을 먼저 비교하느냐가 중요하다. 가장 핵심적인 필드부터 비교하자.

``` java
public int compareTo(PhoneNumber pn) {
    int result = Short.compare(areaCode, pn.areaCode); // 가장 중요
    if (result == 0)  {
        result = Short.compare(prefix, pn.prefix); // 두 번째로 중요
        if (result == 0)
            result = Short.compare(lineNum, pn.lineNum); // 세 번째로 중요
    }
    return result;
}
```
* 여기서 Comparator을 사용하면 코드가 더 깔끔해진다. 
``` java
private static final Comparator<PhoneNumber> COMPARATOR = 
    Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
        .thenComparingInt(pn -> pn.prefix)
        .thenComparingInt(pn -> pn.lineNum);

public int compareTo(PhoneNumber pn) {
    return COMPARATOR.compare(this, pn);
}
```