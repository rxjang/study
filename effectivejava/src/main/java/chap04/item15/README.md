# item15. 클래스 멤버의 접근 권한을 최소화하라
잘 설계된 컴포넌트는 어설프게 설계된 컴포넌트보다 내북 구현정보를 외부 컴포넌트로부터 잘 숨긴(정보은닉, 탭슐화) 컴포넌트이다.

### 정보은닉의 장점
* 시스템 개발속도를 높인다.
* 시스템 관리 비용을 낮춘다.
* 성는 최적화에 도움을 준다.
* 소프트웨어 재사용성을 높인다.
* 큰 시스템을 제작하는 난이도를 낮춰준다.

## 접근 제한자
정보 은닉을 위한 기본 원칙은 모든 클래스와 멤버의 접근성을 가능한 좁혀야 한다는 것이다. 즉, 소프트웨어가 올바르게 동작하는 한 항상 낮은 접근 수준을 부여해야 한다.
* private: 멤버를 선언한 톱레벨 클래스에서만 접근할 수 있다.
* package-private: 멤버가 소속된 패키지 안의 모든 클래스에서 접근할 수 있다. (클래스의 기본값)
* protected: 멤버가 소속된 패키지 안의 클래스와 클래스를 상속한 하위 클래스에서만 접근할 수 있다.
* public: 모든 곳에서 접근할 수 있다. (인터페이스 기본값)


## 필드에 public 사용은 가급적 피하라
인스턴스 필드에 public을 사용하면 외부에서 값을 변경할 수 있으므로 의도치 않은 동자작을 유발할 수 있다. 또한 멀티 스레드 환경에서 사용된다면 thread-safe하지 않게 된다. 
public 클래스는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서는 안된다. 

## 배열은 final이어도 값이 변경될 수 있다.
``` java
public static final Thing[] VALUES = { ... };
```
위 배열의 값은 바뀔 수 있다. VALUES의 필드에는 배열의 값이 저장되어 있는 것이 아니라 배열의 값들이 저장되어 있는 메모리를 바라보는 참조값이 저장된다.
이는 다음과 같이 수정해 불변으로 만들어야 한다.
``` java
private static final Thing[] PRIVATE_VALUES = { ... };
public static final List<Thing> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
// or
public static final Thing[] values() {
    return PRIVATE_VALUES.clone();
}
```

## 모듈
자바 9에 추가된 개념으로, 두 가지 암묵적 접근 수준이 추가되었다. 모듈은 자신이 속하는 패키지 중 공개할 것들을 선언한다.(관례상 module-info.java 에)
protected 혹은 public 멤버라도 해당 패키지를 공개하지 않았다면 모듈 외부에서는 접근할 수 없다. 