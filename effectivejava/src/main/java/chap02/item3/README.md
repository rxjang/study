# 아이템3. private 생성자나 열거타임으로 싱글턴임을 보증하라.
## 싱글톤 패턴
* 인스턴스를 오직 하나만 생성할 수 있는 클래스
* 크게 세가지 방식으로 구현 가능

### public static final 방식
``` java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { ... }
    
    public void leaveToBuilding() { ... }
}
```
* 셍성자가 private으로 선언되어 외부 호출 불가
* INSTANCE가 ELVIS 클래스의 유일한 객체  

`장점`
* 해당 클래스가 싱글턴임이 명백히 드러남
* 간결함

### 정적 팩터리 방식
``` java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() { ... }
    public static Elvis getInstance() {
        return INSTANCE;
    }
    
    public void leaveTheBuilding() { ... }
}
```
* 이 또한 생성자가 private으로 되어있어 외부에서 생성이 불가능하다.
* getInstance를 통해 항상 같은 객체의 참조의 반환을 보장한다.

`장점`
* 싱글톤이 아니게 바꾸거나 상황에 따라 다른 객체를 반환하도록 코드를 수정해도 getInstace 메소드를 사용하는 코드에는 영향이 없다. 
* 간결함

### 열거 타입 방식
``` java
public enum Elvis {
    INSTANCE;
    public void leaveTheBuilding() {...}
}
```
* 매우 간결, 추가 노력 없이 직렬화 가능
* 제 2의 인스턴스가 생기는 일을 완벽히 막아줌
* but 상속이 필요하다만 이 방법을 사용할 수는 없음