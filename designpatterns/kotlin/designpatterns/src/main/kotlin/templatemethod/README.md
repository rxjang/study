# Template Method Pattern

```kotlin
// 커피를 만들기 위한 클래스
class Coffee {

    fun prepareRecipe() {
        boilWater()
        brewCoffeeGrinds()
        pourInCup()
        addSugarAndMilk()
    }

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun brewCoffeeGrinds() {
        println("필터를 통해서 커피를 우려내는 중")
    }

    fun pourInCup() {
        println("컵에 따르는 중")
    }

    fun addSugarAndMilk() {
        println("설탕과 우유를 추가하는 중")
    }
}

// 홍차를 만들기 위한 클래스
class Tea {

    fun prepareRecipe() {
        boilWater()
        steepTeaBag()
        addLemon()
        pourInCup()
    }

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun steepTeaBag() {
        println("차를 우려내는 중")
    }

    fun addLemon() {
        println("레몬을 추가하는 중")
    }

    fun pourInCup() {
        println("컵에 따르는 중")
    }
}
```
보다싶이 코드가 많이 중복되어있다. 중복되어있으면 디자인을 고쳐야 하지 않을까 생각해 보는게 좋다. 
공통적인 부분을 추상화시켜 베이스 클래스를 만들어보자.   
만드는 법을 다시 한번 보면 두 가지 만드는 법의 알고리즘이 똑같다는 것을 알 수 있다. 
1. 물을 끓인다. 
2. 뜨거운 물을 이용하여 커피 또는 홍차를 우려낸다.
3. 만들어진 음료를 컵에 따른다. 
4. 각 음료에 맞는 첨가물을 추가한다.  

이제 위 방법을 추상화해서 coffee와 tea에 적용해보자
```kotlin
abstract class CaffeineBeverage {

    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCoup()
        addCondiments()
    }

    abstract fun brew()
    abstract fun addCondiments()

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun pourInCoup() {
        println("컵에 따르는 중")
    }
}

class Tea(): CaffeineBeverage() {
    override fun brew() {
        println("차를 우려내는 중")
    }

    override fun addCondiments() {
        println("레몬을 추가하는 중")
    }
}

class Coffee(): CaffeineBeverage() {
    override fun brew() {
        println("필터로 커피를 우려내는 중")
    }

    override fun addCondiments() {
        println("설탕과 커피를 추가하는 중")
    }
}
```
이렇게 우리가 적용한 것을 템플릿 메소드 패턴이라고 한다.
> 템플릿 메소드 패턴
> 메소드에서 알고리즘의 골격을 정의한다. 알고리즘의 여러 단계 중 일부는 서브클래스에서 구현할 수 있다. 
> 템플릿 메소드를 이용하면 알고리즘의 구조는 그대로 유지하면서 서브 클래스에서 특정 단계를 재정의할 수 있다.  

즉, 템플릿 메소드 패턴을 간단히 정의하면 알고리즘의 틀을 만들기 위한 것이다. 틀은 그냥 메소드에 불과하다. 구체적으로 일련의 단계들로 알고리즘을 정의한 메소드이다.

## 후크
> 후크(hook)는 추상 클래스에 선언되는 메소드긴하지만 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드이다.  

후크를 통해 서브클래스 입장에서는 다양한 위치에서 알고리즘에 끼어들 수 있다. 아래 예를 살펴보자.
```kotlin
abstract class CaffeineBeverageWithHook {

    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCoup()
        if (customerWantsCondiments()) {
            addCondiments()
        }
        addCondiments()
    }

    abstract fun brew()
    abstract fun addCondiments()

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun pourInCoup() {
        println("컵에 따르는 중")
    }
    
    fun customerWantsCondiments(): Boolean {
        return true
    }
}
```
위 예시에서는 customerWantsCondiments()가 후크가 된다. 후크는 아래와 같은 특징이 있다. 
* 알고리즘에서 필수적이지 않은 부분을 필요에 따라 서브클래스에서 구현하든 말든 하도록하는 경우에 쓸 수 있다.
* 템플릿 메소드에서 앞으로 일어날 일 또는 막 일어난 일에 대해 서브클래스에서 반응할 기회를 제공하기 위한 용도로 쓸 수 있다. 

## 할리우드 원칙
> 먼저 연락하지 마세요. 저희가 연락 드리겠습니다.  

할리우드 원칙을 활용하면 **의존성 부패**를 방지할 수 있다. 의존성이 복잡하게 꼬여있는 것을 의존성 부패라고 부른다. 
의존성이 부패되면 시스템이 어떤 식으로 디자인된 것인지 알아볼 수 없다. 
이 때 할리우드 원칙을 사용하면, 저수준 구성요소에서 시스템이 접속을 할 수 있지만, 언제 어떤 식으로 그 구성요소들을 사용할지는 고수준 구성요소에서 결정하게 된다.  