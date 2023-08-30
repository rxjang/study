# item65. 리플렉션보다는 인터페이스를 사용하라

## 리플렉션
`java.lang.reflect`에 있는 리플렉션 기능을 이용하면 프로그램에서 임의의 클래스에 접근할 수 있다. 
* 클래스 객체가 주어지면 그 클래스의 Constructor, Method, Field의 인스턴스를 가져올 수 있다.
* 이 인스턴스들로는 그 클래스의 멤버 이름, 필드 타입, 메서드 시그니처 등을 가져올 수 있다. 
* 인스턴스를 통해 해당 클래스의 인스턴스를 생성, 메서드를 호출, 필드에 접근할수 있다. 
* eg) `Method.invoke`를 통해 어떤 클래스의 어떤 객체가 가진 어떤 메서드라도 호출할 수 있다. 
* 컴파일 당시에 존재하지 않던 클래스도 이용할 수 있다.

## 리플렉션의 단점
* 컴파일 타입 검사가 주는 이점을 하나도 누릴 수 업사.
  * eg) 예외검사
* 코드가 지저분하고 장황해진다.
* 성능이 떨어진다. 
  * 리플렉션을 통한 호출은 일반 메서드 호출보다 훨씬 느리다.  
``` java
public static void main(String[] args) {
    // 클래스 이름을 Class 객체로 변환
    Class<? extends Set<String>> cl = null;
    try {
        cl = (Class<? extends Set<String>>) Class.forName(args[0]);
    } catch (ClassNotFoundException e) {
        System.out.println("클래스를 찾을 수 없습니다.");
    }

    // 생성자를 얻는다.
    Constructor<? extends Set<String>> cons = null;
    try {
        cons = cl.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
        System.out.println("매개변수 없는 생성자를 찾을 수 없습니다.");
    }

    // 집합의 인스턴스를 만든다.
    Set<String> s = null;
    try {
        s = cons.newInstance();
    } catch (IllegalAccessException e) {
        System.out.println("생성자에 접근할 수 없습니다.");
    } catch (InstantiationException e) {
        System.out.println("클래스를 인스턴스화 할 수 없습니다.");
    } catch (InvocationTargetException e) {
        System.out.println("생성자가 예외를 던졌습니다: " + e.getCause());
    } catch (ClassCastException e) {
        System.out.println("Set을 구현하지 않은 클래스입니다.");
    }

    // 생성한 집합을 사용한다.
    s.addAll(Arrays.asList(args).subList(1, args.length));
    System.out.println(s);
} 
```
## 정리
* 리플렉션은 아주 제한된 형태로만 사용하자
* 되도록 객체 생성에만 사용하자
  * 생성한 객체를 이용할 때는 적절한 인터페이스나 컴파일 타임에 알 수 있는 상위클래스로 형변환해 사용하자.
