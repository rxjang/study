# item22. 일반적인 알고리즘을 구현할 때 제네릭을 사용하라
아규먼트로 함수에 값을 전달할 수 있는 것퍼럼, 타입 아규먼트를 사용하면 함수에 타입을 전달할 수 있다.
타입 아규먼트를 사용하는 함수를 **제네릭 함수**라고 부른다. 대표적인 예로는 stdlib에 있는 filter함수가 있다.
```kotlin
inline fun <T> Iterable<T>.filter(
    predicate: (T) -> Boolean
): List<T> {
    val destination = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            destination.add(element)
        }
    }
}
```
타입 파라미터는 컴파일러에 타입과 관련된 정보를 제공해 컴파일러가 타입을 조금이라도 더 정확하게 추측할 수 있게 해준다.  
제네릭은 기본적으로 `List<String>` 또는 `Set<User>`처럼 구체적인 타입으로 컬렉션을 만들 수 있게 클래스와 인터페이스에 도입된 기능이다. 
컴파일 타입에서는 최종적으로 이러한 정보는 사라지지만 개발 중에는 특정 타입의 사용을 강제할 수 있다. 
이와 같은 기능은 정적 타입 프로그래밍 언어에서는 유용하게 활용된다.

## 제네릭 제한
타입 파라미터의 중요한 기능 중 하나는 구체적인 타입의 서브타입만 사용하게 타입을 제한하는 것이다. 
```kotlin
fun <T: Comparable<T>> Iterable<T>.sorted(): List<T> {
    /*...*/
}

fun <T, C :MutableCollection<in T>> Iterable<T>.toCollection(destination: C): C {
    /*...*/
}

class ListAdapter<T: ItemAdapter>(/*...*/) { /*...*/ }
```
위의 예제처럼 타입에 제한이 걸리므로, 내부에서 해당 타입이 제공하는 메서드를 사용할 수 있다.  
많이 사용하는 제한으로는 Any가 있다. 이는 nullable이 아닌 타입을 나타낸다.
```kotlin
inline fun <T, R: Any> Iterable<T>.mapNotNull(
    transform: (T) -> R?
): List<R> {
    return mapNotNullTo(ArrayList<R>(), transform)
}
```

## 정리
코틀린 자료형 시스템에서 타입 파라미터는 굉장히 중요한 부분이다. 일반적으로 이를 사용해 type-safe 제네릭 알고리즘과 제네릭 객체를 구현한다.
타입 파라미터는 구체 자료형의 서브타입을 제한할 수 있다. 이렇게 하면 특정 자료형이 제공하는 메서드를 안전하게 사용할 수 있다. 