# item30. 요소의 가시성을 최소화 하라
간결한 API를 선호하는 이유를 몇 가지 살펴보자.  

작은 인터페이스는 배우기 쉽고 유지하기 쉽다. 기능이 많은 클래스보다는 적은 클래스를 이해하는 것이 쉽고 유지보수하기 쉽다.  

변경을 가할 때는 기존의 것을 숨기는 것보다 새로운 것을 노출하는 것이 쉽다. 일반적으로 공개적으로 노출되어 있는 요소들은 외부에서 사용할 수 있고, 이런 요소들을 변경하면 이 코드를 사용하는 모든 부분이 영향을 받는다.  

클래스의 상태를 나타내는 프로퍼티를 외부에서 변경할 수 있다면 클래스는 자신의 상태를 보장할 수 없다. 아래의 예시에서 elementsAdded의 세터를 private으로 설정했었다. 이 부분이 없다면 외부에서 이 코드를 강제로 바꿀 수 있어 신뢰성 문제가 생길 수 있다.
```kotlin
class CounterSet<T>(
    private val innerSet: MutableSet<T> = setOf()
) : MutableSet<T> by innerSet {
    var elementsAdded: Int = 0
        private set

    override fun add(element: T): Boolean {
        elementAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elementsAdded += elements.size
        return innerSet.addAll(elements)
    }
}
```
코틀린에서는 이처럼 구체 접근자의 가시성을 제한해서 모든 프로퍼티를 캡슐화 하는 것이 좋다.

서로서로 의존하는 프로퍼티가 있을 때는 객체 상태를 보호하는것이 더 중요하다. 
mutableLazy 델리게이트를 구현할 때를 생각해보자. initialized가 true라면 값 초기화가 이루어지가 이 때의 값은 T타입이라는 것을 예상할 수 있다.
이 때 initialized의 세터가 노출되어서는 안된다.

```kotlin
class MutableLazyHolder<T>(val initializer: () -> T) {
    private var value: Any = Any()
    private val initialized = false

    override fun get(): T {
        if (!initialzed) {
            value = initializer()
            initialzed = true
        }
        return value as T
    }

    override fun setValue() {
        this.value = value
        initialzed = true
    }
}
```
가시성이 제한될수록 클래스의 변경을 쉽게 추적할 수 있으며, 프로퍼티의 상태를 더 쉽게 이해할 수 있다. 이는 동시성을 처리할 떄 중요하다.

## 가시성 한정자 사용하기
내부적인 변경 없이 작은 인터페이스를 유지하고 싶다면 가시성을 제한하면 된다.  
클래스 멤버의 경우 다음과 같은 4개의 가시성 한정자를 사용할 수 있다. 
* public: 어디에서나 볼 수 있다.
* private: 클래스 내부에서만 볼 수 있다.
  * 동일한 파일 또는 클래스에서만 사용되게 만들고 싶을 때
* protected: 클래스와 서브클래스 내부에서만 볼 수 있다.
  * 요소가 상속을 위해 설계되어 있고, 클래스와 서브클래스에서만 사용되게 만들고 싶다면
* internal: 모듈 내부에서만 볼 수 있다.
  * 모듈이 다른 모듈에 의해 사용될 가능성이 있을 때

이러한 규칙은 데이터를 저장하도록 설계된 클래스에는 적용하지 않는 것이 좋다. 데이터를 저장하도록 설계된 클래스는 숨길 이유가 없기 때문이다.

톱레벨 요소에는 세 가지 가시성 한정자를 사용할 수 있다.
* public: 어디에서나 볼 수 있다.
* private: 같은 파일 내부에서만 볼 수 있다. 
* internal: 모듈 내부에서만 볼 수 있다.

모듈과 패키지를 의미가 전혀 다르다. 코틀린에서 모듈은 함께 컴파일되는 코틀린 소스를 의미한다.
* 그레이들 소스 세트
* 메이븐 프로젝트
* 인텔리제이 IDEA 모듈
* 앤트 태스트 한번으로 컴파일 되는 파일 세트
