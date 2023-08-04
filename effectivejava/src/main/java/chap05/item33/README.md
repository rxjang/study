# Item 33. 타입 안전 이종 컨테이너를 고려하라

제네릭은 하나의 컨테이너에서 매개변수화할 수 있는 타입의 수가 제한된다.

하지만, 컨테이너 자체가 아닌 ‘키’를 매개변수화 한 다음, 컨테이너에 값을 넣거나 뺄 때 매개변수화한 키를 함께 제공하면 더 유연하게 여러 타입을 받을 수 있다. 이를 `타입 안전 이종 컨테이너 패턴` 이라고 한다.

```java
public <T> void putFavorite(Class<T> type, T instance);
public <T> T getFavorite(Class<T> type);
```

### 구현 예시

```java
public class Favorites{
    private Map<Class<?>, Object> favorites = new HashMap<>();

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
```

- `Map<Class<?>, Object>` 키를 **비한정적인 와일드카드 타입**으로 선언하였기 때문에, 이를 통해서 **다양한 매개변수화 타입의 키**를 허용할 수 있게 되었다. 만약 `Map<Class<T>, Object>` 였다면 오직 한가지 타입의 키만 담을 수 있었을 것이다.
- `Class.cast` value가 Object 타입이므로 맵에 넣을때 값이 키 타입의 인스턴스라는 것이 보장되지 않는다. 따라서 맵에서 가져올때는 cast()를 사용해 이 객체 참조를 class 객체가 가리키는 T 타입으로 동적 변환하고 있다.

```java
public static void main(String[] args) {
     Favorites f = new Favorites();
        
     f.putFavorite(String.class, "Java");
     f.putFavorite(Integer.class, 0xcafebabe);
     f.putFavorite(Class.class, Favorites.class);
       
     String favoriteString = f.getFavorite(String.class);
     int favoriteInteger = f.getFavorite(Integer.class);
     Class<?> favoriteClass = f.getFavorite(Class.class);
        
     System.out.printf("%s %x %s%n", favoriteString,
                favoriteInteger, favoriteClass.getName()); 
 }
```

## 타입 안전 이종 컨테이너의 한계

### Class 객체를 제네릭이 아닌 로 타입으로 넘기면, 타입 안전성이 쉽게 깨진다.

```java
f.putFavorite((Class)Integer.class, "문자열");
int result = f.getFavorite(Integer.class);
```

→ 컴파일 시점에는 문제 없이 저장되나, 꺼내올 때 String 객체를 Integer로 캐스팅하면서 런타임 예외가 발생한다.

```java
public <T> void putFavorite(Class<T> type, T instance) {
      favorites.put(Objects.requireNonNull(type), type.cast(instance));
 }
```

→ 위와 같이 동적 형변환을 통해 인수로 주어진 instance의 타입이 type으로 명시한 타입과 같은지 확인하면 된다.

### 실체화 불가 타입에서 사용할 수 없다.

- `List<String>` 과 같이 제네릭 타입은 하나의 List.class를 공유하지 고유의 Class 객체가 없기 때문에, String 이나 String[] 와 다르게 키로 저장하려 하면 컴파일 에러가 발생한다.
- 그럼에도 제네릭을 저장하고 싶다면, 대안으로 스프링에서 제공하는 슈퍼타입 토큰인 `ParameterizeTypeReference` 을 활용해야 한다.

## 한정적 타입 토큰

Favorite은 현재 어떤 Class객체든 받아들이므로 **비한정적 타입 토큰**이다. 한정적 타입토큰을 이용해 이 메서드들이 허용하는 타입을 제한할 수 있다.

> 한정적 타입 매개변수( `E extends Delayed` )나 한정적 와일드카드( `? extends Delayed` )을 사용하여 표현 가능한 타입을 제한하는 토큰
>

```java
public <T extends Annotation> T getAnnotation(Class<T> annotationType);
```

- 토큰으로 명시한 타입의 에너테이션이 대상 요소에 달려있다면 그 애너테이션을 반환하고, 없다면 null을 반환한다.

```java
static Annotation getAnnotation(AnnotatedElement element,
                                    String annotationTypeName) {
     Class<?> annotationType = null;
     try {
         annotationType = Class.forName(annotationTypeName);
     } catch (Exception ex) {
         throw new IllegalArgumentException(ex);
     }
     
     return element.getAnnotation(
            annotationType.asSubclass(Annotation.class));
}
```