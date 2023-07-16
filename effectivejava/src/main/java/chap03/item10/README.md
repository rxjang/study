# item 10. equals는 일반 규약을 지켜 재정의하라

## equals를 재정의 하지 않아도 되는 경우
1. 각 인스턴스가 본질적으로 고유할 때
2. 인스턴스의 논리적 동치성을 검사할 필요가 없을 때
3. 상위 클래스에서 재정의한 equals가 하위 클래스에도 들어맞을 때
4. 클래스가 private이거나 package-private이고 equals 메서드를 호출할 일이 없을 때  
클래스를 만들 때 클래스를 사용할 다른 개발자와의 협업을 위해 공콩 메소드를 재 정의한다. Java에서 모든 클래스는 equals 메소드를 가지고 있고 개발자들은 클래스의 인스턴스를 비교할 때 equals 메소드를 사용한다. 
하지만 클래스가 클래스나 모듈의 외부에서 사용할 수 없도록 정의되어 있다면 그 클래스는 다른 개발자가 사용할 수 없기 때문에 equals 메소드를 재정의할 필요가 없다. 

## equals를 재정의 해야하는 경우
두 객체의 논리적 동치성을 확인해야 하는데, 상위 클래스의 equals가 논리적 동치성을 비교하도록 재정의되지 않았을 때

### equals 일반 규약
equals 메서드는 **동치관계**를 구현하며, 다음을 만족한다.  

규약 1. `반사성` null이 아닌 모든값 x에 대해, x.equals(x)는 true이다.  
객체는 자기 자신과 같아야 함

규약 2. `대칭성` null이 아닌 모든값 x, y에 대해, x.equals(y)가 true이면 y.equals(x)도 true이다.  
두 객체는 서로에 대한 동치 여부에 똑같이 답해야 한다. 
``` java
// 대칭성을 어긴 예시
public class CaseInsensitiveString {
    ...

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) obj).s);
        }
        if (obj instanceof String) {
            return s.equalsIgnoreCase((String) obj);
        }
        return false;
    }
}

CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
String s = "polish";
System.out.println("cis.equals(s) ? " + cis.equals(s)); // true
System.out.println("s.equals(cis) ? " + s.equals(cis)); // false
```

규약 3. `추이성` null이 아닌 모든값 x, y, z에 대해, x.equals(y)가 true이고 y.equals(z)도 true이면 x.equals(z)도 true이다.
``` java
public class Point {
    private final int x;
    private final int y;

    ...

    @Override
    public boolean equals(Object o) {
        
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }
}

public class ColorPoint extends Point{
    private final Color color;

    ...

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;

        // o가 일반 Point면 생상을 무시하고 비교한다.
        if (!(o instanceof ColorPoint)) return o.equals(this);

        // o가 ColorPoint면 색상까지 비교한다.
        return (super.equals(o)) && ((ColorPoint) o).color == color;
    }

}

// 추이성이 위배됨
ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
Point p2 = new Point(1, 2);
ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);

System.out.println("p1.equals(p2) ? " + p1.equals(p2)); // true
System.out.println("p2.equals(p3) ? " + p2.equals(p3)); // true
System.out.println("p3.equals(p1) ? " + p3.equals(p1)); // false
```
ColorPoint와 Point의 자표만 비교했더니 추이성이 위반됐다. **구체 클래스를 확장해 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다.**
그렇다면 아래와 같이 getClass검사로 바꾸면 어떨까?
``` java
@Override 
public boolean equals(Object o) {
    if (o == null || o.getClass() != getClass()) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
}
```
이는 좋아보이지만 리스코프 치환원칙을 위배한 코드가 되었다.
> 리스코프 치환원칙  
> 어떤 타입에 있어 중요한 속성이라면 그 하위 타입에서도 마찬가지로 중요하다. 

리스코프 치환원칙을 위배하지 않으면서 이를 위회할 방법으로 **상속 대신 컴포지션을 사용해** 해결할 수 있다. 
``` java
public class ColorPoint {
    private final Point point;
    private final Color color;
    
    ...
    
    public Point asPoint() {
        return point;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }
}
```

규약 4. `일관성` null이 아닌 모든값 x, y에 대해, 반복적으로 x.equals(y)를 호출해도 그 결과는 동일하다.  
두 객체가 같다면 앞으로도 영원히 같아야 한다.

규약 5. `null이 아님` null이 아닌 모든값 x에 대해, x.equals(null)는 false이다.  
모든 객체가 null과 같지 않아야 한다는 뜻이다. 
``` java
public boolean equals(Object o) {
    if (o == null)
        return false; // 필요 없음
    if (!(o instanceof MyType))
        return false;
    ...
}
```
하지만 위처럼 명시적으로 검사할 필요는 없다. instanceof가 그 역할을 포함하기 때문이다.

## equals 메소드 구현 방법
1. == 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다. 
2. instanceof 연산자로 입력이 올바른 타입인지 확인한다. 
3. 입력을 올바른 타입으로 형변환한다.
4. 입력 객체와 자기 자신의 대응되는 핵심필드들이 모두 일치하는지 하나씩 검사한다.  

**구현을 완료했다면 대칭성, 추이성, 일관성을 검사한다.**
