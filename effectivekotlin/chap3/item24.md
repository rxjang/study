# item24. 제네릭 타입과 variance 한정자를 활용하라
```kotlin
class Cup<T>
```
위 코드에서 타입 파라미터 T는 variance 한정자 (out 또는 in)이 없으므로 기본적으로 invariant(불공변성)이다.
invariance라는 것은 제네릭 타입으로 만들어지는 타입들이 서로 관련성이 없다는 의미이다.

```kotlin
fun main() {
    // Cup<Int>, Cup<Number>, Cup<Any>, Cup<Nothing>은 어떠한 관련성도 갖지 않는다.
    val anys: Cup<Any> = Cup<Int>()
    val nothings: Cup<Noting> = Cup<Int>() // 오류
}
```

관련성을 원한다면 variance 한정자를 붙이자.
* out: 타입 파라미터를 convariant(공변성)으로 만듦
  * A가 B의 서브 타입 일 때, `Cup<A>`가 `Cup<B>`의 서브타입이다.
    ```kotlin
    class Cup<out T>
    open class Dog
    class Puppy: Dog()
    
    fun main(args: Array<String>) {
        val b: Cup<Dog> = Cup<Puppy>() // OK
        val a: Cup<Puppy> = Cup<Dog>() // 오류
        val anys: Cup<Any> = Cup<Int>() // OK
        val notins: Cup<Nothing> = Cup<Int> // 오류
    }
    ```
* in: 타입 파라미터를 contravariant(반변성)으로 만듦
  * A가 B의 서브 타입 일 때, `Cup<A>`가 `Cup<B>`의 슈퍼타입이다.
   ```kotlin
    class Cup<in T>
    open class Dog
    class Puppy: Dog()
    
    fun main(args: Array<String>) {
        val b: Cup<Dog> = Cup<Puppy>() // 오류
        val a: Cup<Puppy> = Cup<Dog>() // OK
        val anys: Cup<Any> = Cup<Int>() // 오류
        val notins: Cup<Nothing> = Cup<Int> // OK
    }
    ```
# 함수 타입
함수 타입은 파라미터 유형과 리턴 타입에 따라서 서로 어떤 관계를 갖는다.
```kotlin
fun printProcessedNumber(transition: (Int) -> Any) {
    print(transition(42))
}
```
(Int)->Any 타입의 함수는 (Int)->Number, (Number)->Any, (Number)->Number, (Number)->Int 로도 작동한다.
```kotlin
val intToDouble: (Int)-> Number = { it.toDouble() }
val numberAsText: (Number) -> Any = { it.toShort() }
val identity: (Number) -> Number = { it }
val numberToInt: (Number) -> Int = { it.toInt() }
val numberHash: (Any) -> Number = { it.hashCode() }
```
**코틀린 함수 타입의 모든 파라미터 타입은 contravariant이다. 또한 모든 리턴 타입은 convariant이다**.  
함수 타입을 사용할 때는 이처럼 자동으로 variance 한정자가 사용된다. 코틀린에서 자주 사용되는 것으로는 convariant를 가진 List가 있다. 
이는 variance 한정자가 붙지 않은 MutableList와 다르다. 어떤 부분이 다른지는 variance 한정자의 안전성과 관련된 내용을 이해하면 알 수 있다.

## variance 한정자의 안전성
자바의 배열은 convariant이다. 배열을 기반으로 제네릭 연산자는 정렬 함수등을 만들기 위해서라고 한다. 그런데 이렇기 때문에 문제가 발생한다. 
``` java
// 컴파일 중에 아무런 문제도 없지만, 런타임 오류가 발생한다.
Integer[] numbers = { 1, 4, 2, 1 };
Object[] objects = numbers;
objects[2] = "B"; // 런타임 오류: ArrayStoreException
```
이는 자바의 명백한 결함이고, 코틀린은 이를 해결하기 위해 Array를 invariant로 만들었다.   
다음 코드를 살펴보자. 파라미터 타입을 예측할 수 있다면 어떤 서브타입이라도 전달할 수 있다. 
```kotlin
open class Dog
class Puppy: Dog()
class Hound: Dog()

fun takeDog(dog: Dog) {}
takeDog(Dog())
takeDog(Puppy())
takeDog(Hound())
```
이는 convariant하지 않다. convariant 타입 파라미터(out 한정자)가 in 한정자 위치에 있다면, convariant와 업캐스팅을 연결해서 우리가 원하는 타입을 아무것이나 전달할 수 있다. 
```kotlin
class Box<out T> {
    private var value: T? = null
  
  // 코틀린에서는 사용할 수 없는 코드
  fun set(value: T) {
      this.value = value
  }
  
  fun get(): T = value ?: error("Value not set")
}

val puppyBox = Box<Puppy>()
val dogBox: Box<Dog> = puppyBox
dogBox.set(Hound()) // puppy를 위한 공간임

val dogHouse = Box<Dog>()
val box: Box<Any> = dogHouse
box.set("Some string") // 하지만 Dog를 위한 공간임
box.set(42) // 하지만 Dog를 위한 공간임
```
이러한 상황은 안전하지 않다. 만약 이렇게 가능하다면 오류가 발생할 것이다. 그래서 코틀린은 public in 한정자 위치에 convariant 타입 파라미터(out 한정자)가 오는 것을 금지하여 이런 상황을 막는다.
```kotlin
class Box<out T> {
  
  var value: T? = null // 오류
  
  fun set(value: T) { // 오류
      this.value = value
  }
  
  fun get(): T = value ?: error("Value not set")
}
```
가시성을 private으로 제한하면 오류가 발생하지 않는다. 객체 내부에서는 업캐스트 객체에 convariant(out 한정자)를 사용할 수 없기 때문이다. 
```kotlin
class Box<out T> {
  
  private var value: T? = null // 오류
  
  private fun set(value: T) { // 오류
      this.value = value
  }
  
  fun get(): T = value ?: error("Value not set")
}
```
convariant(out 한정자)는 public out 한정자 위치에서도 안전하므로 따로 제한되지 않는다. 이러한 안정성의 이유로 생성되거나 노출되는 타입에만 convariant(out 한정자)를 사용하는 것이다.  
이러한 프로퍼티는 일반적으로 producer 또는 immutable 데이터 홀더에 많이 사용된다.  
그 예로 T는 convariant인 `List<T>`가 있다. 지금까지의 이유로 함수의 파라미터가 `List<Any?>`로 예측된다면 별도의 변환 없이 모든 종류를 파림터로 전달할 수 있다. 다만 `MutableList<T>`에서 T는 in한정자 위치에서 사용되며 안전하지 않으므로 invariant이다. 
```kotlin
fun append(list: MutableList<Any>) {
    list.add(42)
}

var strs = mutableListOf<String>("A", "B", "C")
append(strs) // 코틀린에서는 사용 불가
val str: String = str[3]
print(str)
```
또 다른 예는 Response가 있다. Response를 사용하면 다양한 이득을 얻을 수 있다. variance 한정자 덕분에 아래 내용들은 모두 참이 된다. 
* `Response<T>` 라면 T의 모든 서브타입이 허용된다. 예를 들어 `Response<Any>`가 예상된다면, `Response<Int>`와 `Response<String>`이 허용된다.
* `Response<T1, T2>`라면 T1과 T2의 모든 서브타입이 허용된다. 
* `Failure<T1>`라면 T의 모든 서브타입 Failure가 허용된다. 
* convariant와 Nothing 타입으로 인해서 Failure는 오류 타입을 지정하지 않아도 되고, Success는 잠재적인 값을 지정하지 않아도 된다.
```kotlin
sealed class Response<out R, out E>
class Failure<out E>(val error: E): Response<Nothing, E>()
class Success<out R>(val value: R): Response<R, Nothing>() 
```

## variance 한정자의 위치
variance 한정자는 크게 두 위치에 사용할 수 있다. 
1. 선언 부분. 
일반적으로 이 위치에 사용되며, 클래스와 인터페이스 선언에 한정자가 적용된다.
```kotlin
class Box<out T>(val value: T)
val boxStr: Box<String> = Box("Str")
val boxAny: Box<Any> = boxStr
```
2. 클래스와 인터페이스를 활용하는 위치
특정한 변수에면 variance 한정자가 적용됨
```kotlin
class Box<T>(val value: T)
val boxStr: Box<String> = Box("Str")
// 사용하는 쪽의 variance 한정자
val boxAny: Box<out Any> = boxStr
```
모든 인스턴스에 variance 한정자를 적용하면 안 되고, 특정 인스턴스에만 적용해야 할 때 이런 코드를 사용한다.
```kotlin
interface Dog
interface Cutie
data class Puppy(val name: String): Dog, Cutie
data class Hound(val name: String): Dog
dta class Cat(val name: String): Cutie

fun fillWithPuppies(list: MutableList<in Puppy>) {
    list.add(Puppy("Jim"))
    list.add(Puppy("Beam"))
}

val dogs = mutableListOf<Dog>(Hound("Pluto"))
fillWithPuppies(dogs)
println(dogs)
// [Hound(name=Pluto), Puppy(name=Jim), Puppy(name=Beam)]

val animals = mutableListOf<Cutie>(Cat("Felix"))
fillWithPuppies(animals)
println(animals)
// [Cat(name=Felix), Puppy(name=Jim), Puppy(name=Beam)]
```
variance 한정자를 사용하면 위치가 제한될 수 있다. 

---
## 정리
코틀린은 타입 아규먼트의 관계에 제약을 걸 수 있는 굉장히 강력한 제네릭 기능을 지원한다. 코틀린에는 다음과 같은 타입 한정자가 있다.
* 타입 파라미터의 기본적인 variance의 동작은 invarinat이다. 만약 `Cup<T>`라고 하면 타입 파라미터 T는 invariant이다. A가 B의 서브타입이라고 할 때, `Cup<A>`와 `Cup<B>`는 아무런 관계를 갖지 않는다.
* out 한정자는 타입 파라미터를 variant하게 만든다. 만약 `Cup<out T>` 라고 하면, 타입 파라미터 T는 convariant이다. A가 B의 서브타입이라고 할 때, `Cup<A>`는 `Cup<B>`의 서브타입이 된다.
* in 한정자는 타입 파라미터를 contravariant하게 만든다. 만약 `Cup<in T>` 라고 하면, 타입 파라미터 T는 contravariant이다. A가 B의 서브타입이라고 할 때, `Cup<B>`는 `Cup<A>`의 슈퍼타입이 된다.  

코틀린에서는
* List와 Set의 타입 파라미터는 convariant(out 한정자)이다. `List<Any>`가 예상되는 모든 곳에 전달할 숫 있다. 
또한 Map에서 값의 타입을 나태내는 타입 파라미터는 contravarint(out 한정자)이다. Array, MutableList, MutableSet, MutableMap의 타입 파라미터는 invaraint이다. 
* 함수 타입의 파라미터 타입은 contravariant(in 한정자)이다. 그리고 리턴 타입은 (out 한정자)이다.
* 리턴만 되는 타입에는 convariant를 사용한다. 
* 허용만 되는 타입에는 contravvarint를 사용한다. 
