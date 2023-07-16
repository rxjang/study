# item11. equals를 재정의하려거든 hashCode도 재정의하라.

hashCode 또한 equals와 마찬가지로 Object애서 부터 정의되어 있는 공통 메소드이다. hashCode도 지켜야하는 공통 규약이 존재한다.
1. equals가 사용하는 정보가 변경되지 않는다면, hashCode의 값도 일정해야한다. 
2. equals가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 같은 값을 반환해야한다.
3. equals가 두 객체를 다르다고 판단했더라도 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없다.

여기서 hashCode 재정의를 잘못했을 때 문제가 되는 조항은 두 번째다. **논리적으로 같은 객체는 같은 해시코드를 반환해야한다.**
`HashMap`, `HashSet`과 같이  hashCode를 기반으로 하는 클래스의 원소로 사용하는 경우 예상치 못한 버그를 발견할 수 있다.
``` java
PhoneNumber phoneNumber1 = new PhoneNumber(82, 123, 456);
PhoneNumber phoneNumber2 = new PhoneNumber(82, 123, 456);
Set<PhoneNumber> phoneNumbers = new HashSet<PhoneNumber>();
phoneNumbers.add(phoneNumber1);
System.out.println(phoneNumber1.equals(phoneNumber2)); // true
System.out.println(phoneNumbers.contains(phoneNumber2)); // false
```
위 코드의 결과, 두 객체의 equals는 true로 논리적으로 동일하지만, contains는 false를 반환한다. hashCode의 2번 규칙을 지키지 않아서 문제가 발생한 것이다. 

## 올바른 hashCode 정의
1. int 변수 result를 선언 후 equals 비교에 사용하는 핵심필드의 hashCode값으로 초기화한다. 
2. 해당 객체의 나머지 핵심 필드 각각에 대해 다음 작업을 수행한다.
   1. 해당 필드의 해시코드를 계산한다.
      1. 기본 타입 필드: Type.hashCode(f)를 수행. (Type은 기본 타입의 박싱 클래스)
      2. 참조 타입 필드 or 재귀적으로 호출하는 필드: 필드의 hashCode를 재귀적으로 호출
      3. 배열인 필드: 핵심 원소 각각을 별도 필드처럼 다른다. 
   2. 위에서 계산한 해시코드로 reulst를 갱신한다. `result = 31 * result + c`
3. result를 반환한다. 

위를 사용한 PhoneNumber의 hasnCode()는 다음과 같다.
``` java
@Override
public int hashCode() {
    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
}
```

