# Factory pattern
피자 가게를 운영하고 있고, 다음과 같은 코드를 만들었다고 생각해보자.
```kotlin
fun orderPizza(): Pizza {
    val pizza = Pizza()
    
    pizza.prepare()
    pizza.bake()
    pizza.cut()
    pizza.box()
    return pizza
}
```
피자 종류는 한가지가 아니기 위해 피자 종류를 고르기 위한 코드를 추가해야 한다.
```kotlin
fun orderPizza(type: String): Pizza {
    val pizza = when (type) {
        "cheese" -> CheesePizza()
        "greek" -> GreekPizza()
        "pepperoni" -> PepperoniPizza()
        else -> Pizza()
    }

    pizza.prepare()
    pizza.bake()
    pizza.cut()
    pizza.box()
    return pizza
}
```
orderPizza()는 문제점이 하나 있다. 바로 피자 종류가 추가 될 때마다 인스턴스를 만들 구상 클래스를 선택하는 부분이다.  
이 부분은 계속 바뀌는 부분이고, 피자를 준비하고 포장하는 부분은 바뀌지 않는 부분이다. 이제 캡슐화를 해보자.  
```kotlin
class SimplePizzaFactory {
    fun createPizza(type: String): Pizza {
        return when (type) {
            "cheese" -> CheesePizza()
            "greek" -> GreekPizza()
            "pepperoni" -> PepperoniPizza()
            else -> Pizza()
        }
    }
}
```
객체 생성을 처리하는 클래스는 팩토리라고 부른다. 피자가 필요할 때마다 피자 공장에서 피자 하나를 만들어 달라고 생각하면 된다. 
이제 orderPizza()에서는 어떤 피자를 만들어야 하는지 고민하지 않아도 된다.  

이렇게 하면 그냥 문제를 다른 객체로 넘겨버린 것 같아 보이는데 무슨 장점이 있는가?
* SimplePizzaFactory를 사용하는 클라이언트가 많을 수 있다. 피자 객체를 받아서 가격이나 피자에 대한 설명등을 찾아서 활용하는 PizzaShopMenu 라는 클래스도 만들 수 있겠다.
* 피자를 생성하는 작업을 한 클래스에 캡슐화함으로 인해 구현을 변경해야 하는 경우 여기저기 다 고칠필요 없이 팩토리 클래스만 고치면 되는 것이다.  

매소드를 정적 메소드로 선언한 디자인을 본적 있는데 비슷한 것인가?
* 이는 정적 팩토리 메서드라고 불리며 객체를 생성하기 위한 메소드를 실행시키기 위해 객체의 인스턴스를 만들지 않아도된다.
    다만, 서브클래스를 만들어서 객체 생성 메소드의 행동을 변경시킬수 없다는 단점이 있다. 
---
이제 PizzaStore을 고쳐보자. 
```kotlin
class PizzaStore(
    private val pizzaFactory: SimplePizzaFactory,
) {

    fun orderPizza(type: String): Pizza {
        val pizza = pizzaFactory.createPizza(type)

        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()
        return pizza
    }
}
```
**간단한 팩토리(Simple Factory)** 는 디자인 패턴이라고 할 수 없다. 프로그래밍에서 자주 쓰이는 관용구라고 할 수 있다. 하지만 워낙 자주쓰여서 알고 있어야 한다.  

피자 가게가 잘 운영이 되어서 프렌차이즈 사입이 진행된다면 어떻게 하면 좋을까? SimplePizzaFactory를 빼고 서로 다른 팩토리를 만들어 PizzaStore에서 적당한 팩토리를 사용하도록 해보자.
```kotlin
val nyFactory = NYPizzaFactory()
val nyStore = PizzaStore(nyFactory)
nyStore.orderPizza("greek")

val chicagoFactory = ChicagoPizzaFactory()
val chicagoStore = PizzaStore(chicagoFactory)
chicagoStore.orderPizza("greek")
```
하지만 피자 가게 들은 독자적인 방법을 사용하길 원했다. 굽는 방식이 다르다거나 자르는 과정을 다르게 하는 등 말이다. 
그렇다면 피자 가게와 피자 제작 과정 전체를 하나로 묶어주는 프레임 워크를 만들어보자.  
피자를 만드는 활종 자체는 전부 PizzaStore에 국한시키면서 고유의 스타일을 살리도록 해보자.
```kotlin
abstract class PizzaStore {
    
    abstract fun createPizza(type: String): Pizza

    fun orderPizza(type: String): Pizza {
        val pizza = createPizza(type)

        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()
        return pizza
    }
}

class NYPizzaStore: PizzaStore() {
    override fun createPizza(type: String): Pizza {
        return when (type) {
            "cheese" -> CheesePizza()
            "greek" -> GreekPizza()
            "pepperoni" -> PepperoniPizza()
            else -> Pizza()
        }
    }
}

class ChicagoPizzaStore: PizzaStore() {
    override fun createPizza(type: String): Pizza {
        return when (type) {
            "cheese" -> CheesePizza()
            "greek" -> GreekPizza()
            "pepperoni" -> PepperoniPizza()
            else -> Pizza()
        }
    }
}
```
PizzaStore의 orderPizza 메소드에 주문 시스템이 갖춰졌다. 주문 시스템에서는 모든 분점에서 똑같이 진행되어야 한다.
분점에서 달라질 수 있는 것은 피자의 스타일이다. createPizza()를 통해 모든 것을 구현할 수 있다. 
이렇게 설계된 디자인이 바로 팩토리 메소드 패턴이다.
> **팩토리 메소드 패턴** 이 패턴에서는 객체를 생성하기 위한 인터페이스를 정의하는데, 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정하게 만든다.
> 팩토리 매서드 패턴을 이용하면 클래스의 인스턴스를 만드는 일을 서브클래스에게 맡기는 것이다.

팩토리 메서드 패턴에는 크게 생산자(creator) 클래스와 제품(product) 클래스가 존재한다. 
PizzaStore이 생산자 클래스가 된다. 추상클래스로 선언되며, 나중에 서브클래스에서 제품(객체)를 생산하기 위해 구현할 펙토리 메소드(추상 메소드)를 정의힌다.  
Pizza는 제품 클래스이다. 팩토리에서는 제품을 생산하게 된다. 

> **의존성 뒤집기 원칙(Dependency Inversion Principle)** 추상화된 것에 의존하도록 만들어라. 구상 클래스에 의존하도록 만들지 않도록 한다.  

의존성 뒤집기 원칙을 지키기 위해서는 다음과 같은 가이드라인을 따르도록 하자.
* 어떤 변수에도 구상 클래스에 대한 래퍼런스를 저장하지 말자
* 구상 클래스에서 유도된 클래스를 만들지 말자
* 베이스 클래스에 이미 구현되어 있던 메소드를 오버라이드 하지말자.
---
이번에는 원재료 생산을 위한 공장을 만들어보자.
```kotlin
interface PizzaIngredientFactory {
    
    fun createDough(): Dough
    fun createSauce(): Sauce
    fun createCheese(): Cheese
    fun createVeggies(): List<Veggies>
    fun createClam(): Clams
}

abstract class Pizza(
    val name: String = "pizza",
    val dough: Dough = Dough(),
    val sauce: Sauce = Sauce(),
    val cheese: Cheese = Cheese(),
    val pepperoni: Pepperoni = Pepperoni(),
    val veggies: List<Veggies> = listOf(Veggies()),
    val clams: Clams = Clams(),
)

data class CheesePizza(
    private val pizzaIngredientFactory: PizzaIngredientFactory,
): Pizza(
    name = "cheesePizza",
    dough = pizzaIngredientFactory.createDough(),
    sauce = pizzaIngredientFactory.createSauce(),
    cheese = pizzaIngredientFactory.createCheese()
) 
```
이제 피자 클래스를 지역별로 따로 만들 필요가 없다. 원재료 공장에서 이를 커버해주기 때문이다. PizzaStore도 아래와 같이 수정된다.
```kotlin
class NYPizzaStore: PizzaStore() {
    override fun createPizza(type: String): Pizza {
        val ingredientFactory = NYPizzaIngredientFactory()
        return when (type) {
            "cheese" -> CheesePizza(ingredientFactory)
            "greek" -> GreekPizza(ingredientFactory)
            "pepperoni" -> PepperoniPizza(ingredientFactory)
            else -> throw RuntimeException()
        }
    }
}
```
여기서는 추상 팩토리를 도입했다. 
추상 팩토리를 통해서 제품군을 생성하기 위한 인터페이스를 제공할 수 있다. 이 인터페이스를 이용하는 코드를 만들면 코드를 제품을 생산하는 실제 팩토리와 분리시킬 수 있다. 
> **추상 팩토리 패턴**에서는 인터페이스를 이용하여 서로 연관된 또는 의존하는 객체를 구상 클래스를 지정하지 않고도 생성할 수 있다.  
