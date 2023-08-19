# item52. 다중정의는 신중히 사용하라

## 잘못된 컬렉션 분류기 - 오버로딩
```java
public class CollectionClassifier {
    public static String classify(Set<?> s) {
        return "Set";
    }

    public static String classify(List<?> lst) {
        return "List";
    }

    public static String classify(Collection<?> c) {
        return "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        for (Collection<?> c : collections)
            System.out.println(classify(c));
    }
}
```
실제로 프로그램을 출력하면 "Unknown Collection" 만 세번 출력한다. 
**다중 정의(overloading)된 세 classify중 어느 메서드를 호출할지가 컴파일 타입에 정해지기 때문이다.**
컴파일타임에는 for문 안의 c는 항상 Collection<?> 타입이다. 위 코드는 아래와 같이 해결할 수 있다. 
``` java
public static String classify(Collection<?> c) {
    return c instanceof Set  ? "Set" :
            c instanceof List ? "List" : "Unknown Collection";
}
```

## 제정의된 호출 매커니즘 - 오버라이딩
```java
class Wine {
    String name() { return "wine"; }
}

class SparklingWine extends Wine {
    @Override String name() { return "sparkling wine"; }
}

class Champagne extends SparklingWine {
    @Override String name() { return "champagne"; }
}

public class Overriding {
    public static void main(String[] args) {
        List<Wine> wineList = List.of(
                new Wine(), new SparklingWine(), new Champagne());

        for (Wine wine : wineList)
            System.out.println(wine.name());
    }
}
```
이번 예시에서는 얘상대로 "wine", "sparkling wine", "champagne" 이 출력된다. 왜 그런것일까?  
**재정의(overriding)한 메서드는 동적으로 선택되고, 다중정의(overloading)한 메서드는 정적으로 선택되기 때문이다.**

## 오버로딩이 혼동을 일으키는 상황은 피하자.
* 안전하고 보수적으로 가려면 매개변수의 수가 같은 오버로딩 메서드는 만들지 말자.
  * 가변인수를 사용하는 메서드라면 오버로딩은 아예 하지 말자.
* 오버로딩대신 메서드 이름을 다르게 지어주는 길도 열려있다. 
* 생성자는 이름을 다르게 지을 수 없으니 두 번째 생성자부터는 무조건 다중정의가 된다. 
  * 정적 팩터리로 대안을 활욜할 수 있다. 

## List의 메소드 오버로딩
``` java
Set<Integer> set = new TreeSet<>();
List<Integer> list = new ArrayList<>();

for (int i = -3; i <= 3; i++) {
    set.add(i);
    list.add(i);
}
for (int i = 0; i <= 3; i++) {
    set.remove(i);
    list.remove(i);
}
System.out.println(set + " " + list);
```
위 코드에서는 set의 경우 -3, -2, -1이 남아있지만, 리스트에는 -2, 0, 2가 남아 있다. 
List가 remove(Object obj)와 remove(int index)가 오버로딩 되어있기 때문이다. 이는 메소드 오버로딩이 개발자의 실수를 유발하는 대표적인 사례이다.

## 핵심 정리
* 일반적으로 매개변수의 수가 같을 때는 다중정의를 피하자.
* 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록하자.
  * 이것이 불가능하면, 예컨대 기존 클래스를 수정해 새로운 인터페이스를 구현해야 할 때는 같은 객체를 입력 받는 다중정의 메서드들이 모두 동일하게 동작하도록 만들자.