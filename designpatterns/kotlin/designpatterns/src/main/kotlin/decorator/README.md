# Decorator Pattern
## 개요
여기, 스타버즈라는 급속도로 성장한 초대형 커피 전문점이 있다. 
워낙 빠르게 성장해 다양한 음료를 모두 포관하는 주문 시스템을 갖추려고 한다. 
초반의 구성은 다음과 같다.

```kotlin
abstract class Beverage(
    val description: String
) {
    
    abstract fun cost(): Int
}

class HouseBlend(): Beverage("HouseBlend") {
    override fun cost(): Int {
        return 1000
    }
}

class DarkRoast(): Beverage("DarkRoast") {
    override fun cost(): Int {
        return 2000
    }
}
```
* Beverage는 음료를 나타내는 추상 클래스이며, 커피샵에서 판매되는 모든 음료는 이 클래스의 서브클래스가 된다. 
* cost()는 추상 메서드로, 모든 서브 클래스에서 구현해야한다.

커피에는 스팀 우유나 두유로 변경하고, 모카를 추가하는 등 많은 커스텀 주문이 존재한다. 
위의 방향으로 설계하다 보니 결국은 클래스 갯수가 폭발적으로 늘어나게 되었다.
```kotlin
class HouseBlendSteamMilkAndMocha(): Beverage("HouseBlendSteamMilkAndMocha") {
    override fun cost(): Int {
        return 6000
    }
}

class HouseBlendWithWhipandSoy(): Beverage("HouseBlendWithWhipandSoy") {
    override fun cost(): Int {
        return 5500
    }
}
// ... 등등....
```
이렇게 설계하면 클래스 관리가 쉽지 않음을 알 수 있다. 우유 가격이 인상하면 모든 클래스를 고쳐야 하고, 카라멜 토핑을 추가하면 새로운 클래스를 추가해야할 것이다.  
그렇다면 이번에는 인스턴스 변수를 추가하고 클래스 상속을 써서 추가사항을 관리해보자.
```kotlin
abstract class Beverage(
    val description: String,
    open val milk: Int?,
    open val soy: Int?,
    open val mocha: Int?,
    open val whip: Int?,
) {

    fun hasMilk(): Boolean = milk != null
    fun hasSoy(): Boolean = soy != null
    fun hasMocha(): Boolean = mocha != null
    fun hasWhip(): Boolean = whip != null

    open fun cost(): Int {
        var cost = 0
        if (hasMilk()) cost += 1_000
        if (hasSoy()) cost += 500
        if (hasMocha()) cost += 500
        if (hasWhip()) cost += 500
        return cost
    }
}

class DarkRoast(
    override val milk: Int?,
    override val soy: Int?,
    override val mocha: Int?,
    override val whip: Int?,
): Beverage("DarkRoast", milk, soy, mocha, whip) {

    override fun cost(): Int {
        return 2_000 + super.cost()
    }

}
```
처음 구현보다는 괜찮아 보인다. 하지만 위 코드에도 문제가 존재한다.
* 첨가물 가격이 바뀔 때마다 기존 코드를 수정해야한다.
* 첨가물의 종류가 많아지면 새로운 메소드를 추가해야하고 수퍼 클래스의 cost도 고쳐야한다. 

스타버즈 커피의 문제는 **객체를 동적으로 구성해 기존 코드 대신 새로운 코드를 만들어서 새로운 기능을 추가하게 함**으로서 해결할 수 있다. 이는 아래의 객체지향 원칙과 관련이 있다.
> OCP(Open-Closed Principle) 클래스는 확장에 대해서는 열려 있어야 하지만 코드 변경에 대해서는 닫혀있어야 한다.   

또, 이 객체지향 원칙을 활용해 다음 패턴을 배울 수 있다.
> **데코레이터 패턴**에서는 객체에 추가적인 요건을 동적으로 첨가한다. 데코레이터는 서브클래스를 만드는 것을 통해서 기능을 유연하게 확장할 수 있는 방법을 제공한다.  

* 데코레이터의 수퍼클래스는 자신이 장식하고 있는 객체의 수퍼클래스와 같다.
* 한 객체를 여러 개의 데코레이터로 감쌀 수 있다.
* 데코레이터는 자신이 감싸고 있는 객체와 같은 수퍼클래스를 가지고 있기 때문에 원래 객체가 들어갈 자리에 데코레이터 객체를 집어 넣어도 상관 없다.
* **데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위임하는 것 외에 원하는 추가적인 작업을 수행할 수 있다.**
* 객체는 언제든지 감쌀 수 있기 때문에 실행중에 필요한 데코레이터를 마음대로 적용할 수 있다.

이를 활용해 스타버즈 커피의 문제를 해결해보자.
```kotlin
abstract class Beverage(
    open val description: String
) {
    abstract fun cost(): Int
}

class HouseBlend(): Beverage("HouseBlend") {
    override fun cost(): Int = 1_000
}

class DarkRoast(): Beverage("DarkRoast") {
    override fun cost(): Int = 2_000
}

abstract class CondimentDecorator(
    override val description: String,
): Beverage(description) {
}

class Whip(
    val beverage: Beverage,
): CondimentDecorator("${beverage.description} Whip") {

    override fun cost(): Int {
        return 500 + beverage.cost()
    }
}

class Soy(
    val beverage: Beverage,
): CondimentDecorator("${beverage.description} Soy") {

    override fun cost(): Int {
        return 5000 + beverage.cost()
    }
}
```
* CondimentDecorator에서 Beverage를 확장하고 있어 상속을 하고 있지만, 데코레이터의 형식이 그 데코레이터로 감싸는 객체의 형식과 같다는 점이 즁요한 포인트이다.
  * 상속을 이용해 형식을 맞추나, 상속을 통해서 행동을 물려받는 것이 아님!
* Beverage는 인터페이스를 사용해도되나, 기존에 추상 클래스를 사용하고 있기 때문에 호환성을 위해 그대로 추상 클래스를 사용하게 되었다.

이제 음료를 주문해 보자. 
```kotlin
fun main() {
    val beverage: Beverage = DarkRoast()
    println(" ${beverage.description}: ${beverage.cost()} ₩")
    var beverage2: Beverage = DarkRoast()
    beverage2 = Soy(beverage2)
    beverage2 = Whip(beverage2)
    beverage2 = Whip(beverage2)
    println(" ${beverage2.description}: ${beverage2.cost()} ₩")
    /**
     *  결과
     *  DarkRoast: 2000 ₩
     *  DarkRoast Soy Whip Whip: 4000 ₩
     */
}
```
* 데코레이터 패턴은 구성 요소를 확인한 다음에 추가적인 작업이 필요한 경우에는 사용할 수 없다.
* 데코레이터는 그 데코레이터가 감싸고 있는 객체에 행도응ㄹ 추가하기 위해 만들어 진것이다. 여러 단계의 데코레이터를 파고 들어가서 작업을 수행해야한다면 이는 데코레이터 패턴과 맞지 않는다.

## 데코레이터 사용 예시: Java I/O
데코레이터 패턴을 쓴 대표적인 예시가 바로 java I/O 이다. java.io 내부의 대부분이 데코레이터 패턴으로 만들어 져 있다.  
* BufferedInputStream과 LineNumberInputStream은 모두 FilterInputStream을 확장한 클래스이다.
* FilterInputStream은 추상 데코레이터 클래스 역할을 한다.
