# item21. 일반적인 프로퍼티 패턴은 프로퍼티 위임으로 만들어라
코틀린은 재사용과 관련해서 프로퍼티 위임이라는 새로운 기능을 제공한다. 
프로퍼티 위임을 사용하면 일반적인 프로퍼티의 행위를 추출해서 재사용할 수 있다.

### 지연(lazy) 프로퍼티
이후에 처음 사용하는 요청이 들어올 때 초기화되는 프로퍼티

```kotlin
val value by lazy { createValue() }
```

프로퍼티 위임을 사용하면, 이외에도 변화가 있을 때 이를 감지하는 observable 패턴을 쉽게 만들 수 있다. 
예를 들어 목록을 출력하는 리스트 어댑터가 있다면, 내부 데이터가 변경될 떄마다 변경된 내용을 출력해야 할 것이다.

```kotlin
 import kotlin.properties.Delegates

 var items: List<Item> by
    Delegates.observable(listOf()) { _, _, _ -> 
        notifyDateSetChanged()
    }
```

lazy와 observable 델리게이터는 언어적 관점으로 보았을 때, 그렇게 특별한 것은 아니다.
일반적으로 프로퍼티 위임 메커니즘을 활용하면 다양한 패턴들을 만들 수 있다. 
자바에서는 패턴들을 사용할 때 어노테이션을 많이 사용해야하나, 코틀린은 프로퍼티 위임을 사용해서 간단하고 type-safey하게 구현할 수 있다.

```kotlin
// 코틀린에서의 의존성 주입
private val presenter: MainPresenter by inject()
private val repository: NetworkRepository by inject()

// 데이터 바인딩
private val port by bindConfiguration("port")
private val token: String by prefereces.bind(TOKEN_KEY)
```

어떻게 이런 코드가 가능하고, 프로퍼티 위임을 어떻게 활용하는지 살펴 볼 수 있도록 간단하게 프로퍼티 델리게이터를 만들어보자.
일부 프로퍼티가 사용될 때, 간단한 로그를 출력해보자.

```kotlin
var token: String? = null
    get() {
        print("token returned value $field")
        return field
    }
    set(value) {
        print("token changed from $field to $value")
        field = value
    }
var attempts: Int = 0
    get() {
        print("attemts returned value $field")
        return field
    }
    set(value) {
        print("attempts changed from $field to $value")
        filed = value
    }
```
두 프로퍼티는 타입이 다르지만 내부적으로 거의 같은처리를 한다. 또한 프로젝트에서 자주 반복될 것 같은 패턴처럼 보이므로 프로퍼티 위임을 활용해 추출하기 좋은 부분이다.
프로퍼티 위임은 다른 객체의 메서드를 활용해서 프로퍼티의 접근자를 만드는 방식이다.

```kotlin

import kotlin.reflect.KProperty

var token: String? by LoggingProperty(null)
var attempts: Int by LoggingProperty(0)

private class LoggingProperty<T>(var value: T) {
    operator fun getValue(
        thisRef: Any?,
        prop: KProperty<*>
    ): T {
        print("${prop.name} returned value $value")
        return value
    }
    
    operator fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        newValue: T
    ) {
        val name = prop.name
        print("$name changed from $value to $newValue")
        value = newValue
    }
}
```
프로퍼티 위임이 어떻게 동작하는지 이해하려면 by가 어떻게 컴파일 되는지 보는 것이 좋다.
```kotlin
@JvmField
private val 'token$delegate' = 
    LoggingProperty<String?>(null)
var token: String?
    get() = 'token$telegate'.getValue(this, ::token)
    set(value) {
        'token$delegate'.setValue(this, ::token, value)
    }
```

코드를 보면 알 수 있듯이 getValue와 setValue는 단순하게 값만 처라하게 바뀌는 것이 아니라 컨텍스트(this)와 프로퍼티 래퍼런스의 경계도 함께 사용하는 형태로 바뀐다. 
프로퍼티에 대한 래퍼런스는 이름, 어노테이션과 관련된 정보 등을 얻을 때 사용된다. 컨텍스트는 함수가 어떤 위치에서 사용되는지와 관련됭 정보를 제공해 준다.  
이러한 정보로 getValue와 setValue가 여러개 있어도 문제 없다. 컨텍스트를 활용하므로 상황에 따라 적절한 메서드가 선택된다.

```kotlin

class SwipeRefreshBinderDelegate(val id: Int) {
    private var cache: SwipeRefereshLayout? = null

    operator fun getValue(
        activity: Activity,
        prop: KProperty<*>
    ): SwipeRefreshLayOut {
        return cache ?: avtivity
            .findViewById<SwipeRefreshLayout>(id)
            .also { cache = it }
    }

    operator fun getValue(
        fragment: Fragment,
        prop: KProperty<*>
    ): SwipeRefreshLayOut {
        return cache ?: fragment.view
            .findViewById<SwipeRefreshLayout>(id)
            .also { cache = it }
    }
}
```

객체를 프로퍼티 위임하려면 val의 경우 getValue 연산, var의 경우 getValue와 setValue 연산이 필요하다. 이러한 연산은 멤버 함수로 만들 수 있지만, 확장함수로도 만들 수 있다.
```kotlin
val map: Map<String, Any> = mapOf(
    "name" to "Marchin",
    "kotlinProgrammer" to true
)
val name by map
print(name )
```
이는 코틀린 stdlib에 다음과 같은 확장 함수가 정의되어 있어서 사용할 수 있는 것이다.

```kotlin
inline oeprator fun <V, V1 : V> Map<in String, V>
        .getValue(thisRef: Any?, property: KProperty<*>): V1 =
    getOrImplicitDefault(property.name) as V1
```
코틀린 stdlib에서 다음과 같은 프로퍼티 델리게이터를 알아두면 좋다.
* lazy
* Delegates.observable
* Delegates.vetoable
* Delegates.notNull
