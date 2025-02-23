# 어댑터 패턴
> 어댑터 패턴은 한 클래스의 인터페이스를 클라이언트에서 사용하고자 하는 다른 인터페이스로 변환한다. 
> 어댑터를 이용하면 인터페이스의 호환성 문제 때무에 같이 쓸 수 없는 클래스들을 연결해서 쓸 수 있다.

1장에서 보았던 오리를 가지고 adapter를 만들어보자
```kotlin
interface Duck {
    fun quack()
    fun fly()
}

class MallardDuck: Duck {
    override fun quack() {
        println("quack")
    }

    override fun fly() {
        println("fly")
    }
}

interface Turkey {
    fun gobble()
    fun fly()
}

class WildTurkey: Turkey {
    override fun gobble() {
        println("gobble gobble")
    }

    override fun fly() {
        println("I'm flying a short distance")
    }

}

class TurkeyAdapter(
    private val turkey: Turkey
): Duck {
    override fun quack() {
        turkey.gobble()
    }

    override fun fly() {
        repeat(5) {
            turkey.fly()
        }
    }
}

fun main() {
    val duck = MallardDuck()
    val turkey = WildTurkey()
    val turkeyAdapter: Duck = TurkeyAdapter(turkey)

    println("The Turkey says..")
    turkey.gobble()
    turkey.fly()

    println("The Duck says..")
    testDuck(duck)

    println("The TurkeyAdapter says....")
    testDuck(turkeyAdapter)
}

fun testDuck(duck: Duck) {
    duck.quack()
    duck.fly()
}
```
**클라이언트에서 어댑터를 사용하는 방법**
1. 클라이언트에서 타겟 인터페이스를 사용하여 메소드를 호출하여 어댑터에 요청을 한다.
2. 어댑터에는 어댑티 인터페이스를 사용하여 그 요청을 어댑티에 대한 (하나 이상의) 메소드 호출로 변환한다. 
3. 클라이언트에서는 호출 결과를 받긴 하지만 중간에 어댑터가 껴 있는지는 전혀 알지 못한다. 

이렇게 어댑터 패턴을 이용해 호환되지 않는 인터페이스를 사용하는 클라이언트를 그대로 활용할 수 있게 되었다. 인터페이스를 변환하는 어댑터를 만들었기 때문이다.   
이렇게 함으로써 클라이언트와 구현된 인터페이스를 분리시킬 수 있으며, 인터페이스가 바뀌더라도 그 변경 내역은 어댑터에 캡술화되기 때문에 클라이언트는 바뀔 필요가 없다.  

## 객체어댑터 & 클래스 어댑터
위에서 배운 내용은 객체 어댑터이다. 클래스 어댑터는 무엇일까? 클래ㅡ 어댑터는 다중 상속이 필요한데 자바는 다중 상속이 불가능해서 이번에는 배우지 않는다. 
다중 상속이 가능한 언어를 사용하는 경우에는 클래스 어댑터를 사용하는 경우가 있을 수 있다. 
**클래스 어댑터에서는 어댑터를 만들 때 카게소가 어댑티 모두의 서브클래스로 만들고, 객체 어댑티에서는 구성을 통해서 어댑티에 요청을 전달한다는 점을 제외하면 별다른 차이가 없다.**
