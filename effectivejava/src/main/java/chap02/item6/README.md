# item6. 불필요한 객체 생성을 피하라

같은 기능의 객체를 매번 생성하기보다 객체 하나를 재사용하는 편이 나을때가 많다. 같은 내용을 가지는 객체를 여러개 생성하는 것은 메모리 상의 낭비를 가져온다.
 
셍성자 대신 정적 팩터리 메서드를 사용하면 불필요한 객체 생성을 피할 수 있다. 생성자는 호출할 때마다 새로운 객체를 만들지만, 팩터리 매서드는 그렇지 않다. 

`eg 1`
``` java
// 실행될 때마다 String 인스턴스를 새로 만든다. 
// 생성자에 넘겨진 "bikini"와 생성자로 만들어내려는 String과 기능적으로 동일함.
String s = new String("bikini");

// 새로운 인스턴스를 매번 만드는 대신, 하나의 String 인스턴스를 사용함.
String s = "bikini";
``` 

`eg 2` 반복해서 필요한 경우 캐싱하여 재사용 하자.
``` java
// Pattern은 유한 상태머신을 만들기 때문에 인스턴스 생성 비용이 높다.
static boolean isRomanNumeral(String s) {
    return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
            + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}

// 미리 생성해둔 Pattern 객체를 재사용 하면 성능이 향상된다. 
private static final Pattern ROMAN = Pattern.compile(
        "^(?=.)M*(C[MD]|D?C{0,3})"
                + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

static boolean isRomanNumeralFast(String s) {
    return ROMAN.matcher(s).matches();
}
```
* 객체기 불변이라면 재사용해도 안전하다. 
* 오토박싱 또한 불필요한 객체를 만들어 낸다. 박싱된 기본타입보다는 기본타이븡ㄹ 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의해야 한다. 
