# item16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라.

## public 클래스는 가변 필드를 직접 노출해서는 안된다. 
``` java
class Point {
    public double x;
    public double y;
}
```
위 클래스의 필드들은 직접 접근할 수 있어 캡슐화를 지키지 못한다. 객체재향적으로 프로그래밍을 한다면 필드를 모두 private으로 바꾸고 public 접근자를 추가해야한다. 
``` java
public class Point {
    private double x;
    private double y;
	
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
	
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
}
```
위와 같이 수정을 하여 x, y값은 Point클래스 내부에서만 수정할 수 있게 하였다. 이제 x, y에 대한 관리책임은 Point 클래스에 있다. 

## 불변 필드라도 직접 노출하는 것은 좋은 방법이 아니다.
``` java
public final class Time {
    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_HOUR = 60;
}
```
위 필드들은 불변 필드여서 public으로 노출했으나, 나중에 문제가 생길 수 있다. API를 변경하지 않고는 표현 방식을 바꿀 수 없고, 필드를 읽을 때 부수 작업을 수행해야한다.
불변식은 이를 보장할 수 있으니 다음과 같이 수정할 수 있겠다.
```java
public final class Time {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    
    public final int hour;
    public final int minute;
    
    public Time(int hour, int minute) {
        if (hour < 0 || hour >= HOURS_PER_DAY)
            throw new IllegalArgumentException("시간: " + hour);
        if (minute < 0 || minute >= MINUTES_PER_HOUR) 
            throw new IllegalArgumentException("분: " + minute);
        this.hour = hour;
        this.minute = minute;
    }
}
```