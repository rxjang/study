# Strategy Pattern
여기 SimUDuck이라는 오리 연못 시뮬레이션 게임을 만드는 회사가 있다.
이 게임에서는 헤엄도 치고 꽥꽥거리기도 하는 다양한 종류의 오리가 있다.  
이 시스템을 처음으로 디자인한 사람들은 표준적인 객체지향 기법을 사용해 Duck이라는 수퍼클래스를 만든 다음, 그 클래스를 확정해 다른 종류의 오리를 만들었다.
```kotlin
abstract class Duck {
    
    open fun quack() {
        println("quack")
    }
    
    fun swim() {
        println("swim")
    }
    
    abstract fun display()
}

class MallardDuck: Duck() {
    override fun display() {
        println("MallardDuck")
    }
}

class RedheadDuck: Duck() {
    override fun display() {
        println("RedheadDuck")
    }
}
```
그런데 오리가 날 수 있어야 한다는 요구 사항이 들어 왔다. 개발자는 fly() 메서드를 추가하기로 했다.
``` kotlin
abstract class Duck {
    ...
    fun fly() {
        println("fly")
    }
}
```
그런데 문제가 발생했다. Duck이라는 수퍼클래스에 fly() 메서드가 추가되면서 일부 서브클래스에 적합하지 않은 행동이 전부 추가된 것이다.
코드의 한 부분만을 바꿈으로해서 프로그램 전체에 부작용이 생긴 것이다.
```kotlin
class RubberDuck: Duck() {

    override fun quack() {
        println("삑삑")
    }

    override fun display() {
        println("RubberDuck")
    }

}
```
그래서 개발자는 quack()과 같이 fly()도 오버라이드해서 아무것도 하지 못하도록했다.
``` kotlin
class RubberDuck: Duck() {
    ...
    override fun fly() {}
}
```
그런데 새로운 클래스를 추가하자 여기도 같은 문제가 발생했다.
```kotlin
class DecoyDuck: Duck() {
    
    override fun quack() {}

    override fun display() {
        println("DecoyDuck")
    }

    override fun fly() {}

}
```
여기서 상속을 사용할 때 다음과 같은 단점을 깨달을 수 있었다.
* 서브클래스에서 코드가 중복된다.
* 실행시에 특징을 바꾸기 힘들다.
* 모든 오리의 행동을 알기 힘들다.
* 코드가 변경했을 때 다른 오리들한테 원치 않은 영향을 끼칠 수 있다. 

## 인터페이스는 어떨까?
위와 같이 요구사항이 계속 추가된다면, 상속을 사용하는 것이 맞는 것일까? 개발자는 이를 인터페이스를 사용해 해결해 보기로했다.
```kotlin
abstract class Duck {
    
    fun swim() {
        println("swim")
    }
    
    abstract fun display()
}

interface Flyable {

    fun fly()

}

interface Quackable {

    fun quack()
}

class MallardDuck: Duck(), Flyable, Quackable {

    override fun display() {
        println("MallardDuck")
    }

    override fun fly() {
        println("fly")
    }

    override fun quack() {
        println("quack")
    }
}

class RedheadDuck: Duck(), Flyable, Quackable {
    override fun display() {
        println("RedheadDuck")
    }

    override fun fly() {
        println("fly")
    }

    override fun quack() {
        println("quack")
    }
}

class RubberDuck: Duck(), Quackable {

    override fun display() {
        println("RubberDuck")
    }

    override fun quack() {
        println("quack")
    }

}

class DecoyDuck: Duck() {

    override fun display() {
        println("DecoyDuck")
    }

}
```
인터페이스를 사용해 일부 문제점을 해결했지만, 그렇게 하면 그러한 행동에 대한 코드 재사용을 할 수 없게되어 코드 관리면에서 또 다른 문제점이 생기게 됐다.  
여기서 우리는 "캡슐화"를 적용할 수 있다. 

> 바뀌는 부분은 따로 뽑아서 캡슐화 시키자. 그렇게 하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않은 채로 그 부분만 고치거나 확정할 수 있다.   

죽, 코드를 변경하는 과정에서 의도하지 않은 일이 일어나는 것을 줄이면서 시스템의 유연성은 향상시킬 수 있는 것이다.  
다시 Duck으로 돌아가서 **변화하는 부분**과 **그렇지 않은 부분**을 분리해 보자. 나는 것, 꽥꽥 거리는 것과 관련된 부분이 변하는 부분이다. 
이러한 행동을 Duck 클래스로부터 갈라내기 위헤서 그 두 메소드를 모두 Duck 클래스로부터 끄집어내서 각 행동을 나타낼 클래스 집합을 새로 만들자.
여기서 우리는 또 다른 디자인 원칙을 살펴볼 수 있다.
> 구현이 아닌 인터페이스에 맞춰서 프로그래밍한다.  
 
인터페이스에 맞춰서 프로그래밍한다는 것은 상위 형식에 맞춰서 프로그래밍한다는 것을 뜻한다.
실제 실행시에 쓰이는 객체가 코드에 의해서 고정되지 않도록, 어떤 상위 형식에 맞춰서 프로그래밍함으로써 다형성을 활용해야 한다.
> **다형성** 이란 프로그래밍 언어에서 하나의 객체가 여러가지 타입을 가질 수 있는 성질을 말한다.  

다형성 예시를 아래 예제를 통해 자세히 확인해 보자.
```kotlin
interface Animal {
    fun makeSound()
}

class Dog: Animal {
    override fun makeSound() {
        bark()
    }

    fun bark() {
        println("bark")
    }
}

class Cat: Animal {
    override fun makeSound() {
        meow()
    }

    fun meow() {
        println("meow")
    }

}

fun main() {
    // 변수 d를 Dog로 선언하면 어떤 구체적인 구현에 맞춰서 코딩을 해야한다.
    val d: Dog = Dog()
    d.bark()

    // Dog라는 걸 알고 있긴 하지만 다향성을 활용해 Animal에 대한 래퍼런스를 써도 된다.
    val animal: Animal = Dog()
    animal.makeSound()

    // 구체적으로 구현된 객체를 실행시에 대입하는 것이 더 바람직하다.
    val a: Animal = getAnimal()
    a.makeSound()
}


fun getAnimal(): Animal {
    ...
}  
```
다시 Duck으로 돌아가서, 인터페이스를 사용해 보자. 

```kotlin
interface FlyBehavior {
    fun fly()
}

class FlyWithWings: FlyBehavior {

    override fun fly() {
        println("Fly With Wings")
    }

}

class FlyNoWay(): FlyBehavior {
    
    override fun fly() {
        println("Fly No Way")
    }

}

interface QuackBehavior {
    fun quack()
}

class Quack: QuackBehavior {
    override fun quack() {
        println("Quack")
    }
}

class Squeak: QuackBehavior {
    override fun quack() {
        println("Squeak")
    }
}

class MuteQuack: QuackBehavior {
    override fun quack() {}
}
```
이런식으로 디자인 하면 다른 형식의 객체에서도 나는 행동과 꽥꽥거리는 행동을 재사용할 수 있다. 
그리고 기존의 행동 클래스를 수정하거나 날아다니는 행동을 사용하는 Duck 클래스를 전혀 건드리지 않고 새로운 행동을 추가할 수 있다. 
이제 Duck에 위 행동들을 인터페이스 형식의 인스턴스 변수로 추가하자.
```kotlin
abstract class Duck(
    private val flyBehavior: FlyBehavior,
    private val quackBehavior: QuackBehavior,
) {
    fun performFly() {
        flyBehavior.fly()    
    }
    
    fun performQuack() {
        quackBehavior.quack()
    }
    
    abstract fun display()
    
    fun swim() {
        println("swim")    
    }
}

class MallardDuck 
    : Duck(flyBehavior = FlyWithWings(), 
            quackBehavior = Quack()) {
    override fun display() {
        println("MallardDuck")
    }
}
```

이제 MallarDuck도 소리를 낼 수 있게 되었다. 그런데 앞서 특정 구현에 맞춰서 프로그래밍을 하지 말라고 했었다. 
앞에 있는 생성자에서는 Quack이라는 구현되어 있는 구상 클래스의 인스턴스를 만들었다.
그렇긴 하지만 실행시에 쉽게 변경할 수 있게 설계되어 있으므로 상당히 유연하다고 할 수 있다.

---
위에서 확인한 Duck은 아래의 디자인 원칙을 사용했다.
> 상속보단 구성을 활용한다.  

"A는 B이다" 보다 "A에는 B가 있다"가 나을 수 있다는 것이다.
두 클래스를 이런 식으로 합치는 것을 **구성(composition)** 을 이용하는 것이라 부른다.
구성을 이용하여 시스템을 만들면 유연성을 크게 향상시킬 수 있다.  
이렇게 Duck을 구성한 디자인 패턴을 스트래티지 패턴이라고 한다.
> **스트래티지 패턴(Strategy Pattern)** 에서는 알고리즘군을 정의하고, 각각을 캡슐화하여 교환해서 사용할 수 있도록 만든다.
> 스트래티지를 활용하면 알고리즘을 사용하는 클라이언트와는 독립적으로 알고리즘을 변경할 수 있다.
