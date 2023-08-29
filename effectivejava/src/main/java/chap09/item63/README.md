# item63. 문자열 연결은 느리니 주의하라.
문자열 연결 연산자(+)는 여러 문자열을 하나로 합쳐주는 편리한 수단이지만 성능 저하가 엄청나다. 
**문자열 연결 연산자로 문자열 n개를 잇는 시간은 n^2에 비례한다.**

## StringBuilder를 사용하라
``` java
public String statement() {
    StringBuilder sb = new StringBuilder(numItems() * LINE_WIDTH);
    for (int i = 0; i < numItems(); i++) {
        b.append(lineForItem(i));
    }    
    return b.toString();
}
```
위 코드에서 String 대신 StringBuilder을 사용하면 약 5.5배정도 빠르다. 