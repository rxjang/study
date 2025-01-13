# item23. 타입 파라미터의 섀도잉을 피하라
**섀도잉**은 프로퍼티와 파라미터가 같은 이름을 가지는 경우를 말한다.
```kotlin
class Forest(val name: String) {
    fun addTree(name: String) {
        // ...
    }
}
```
이러한 섀도잉 현상은 클래스 타입 파라미터와 함수 타입 파라미터 사이에서도 발생한다. 
이는 심각한 문제가 될 수 있으며, 문제를 찾아내기도 힘들다. 
```kotlin
interface Tree
class Birch: Tree
class Spruce: Tree

class Forest<T: Tree> {
    fun <T: Tree> addTree(tree: T) {
        //...
    }
}
```
위의 예시에서는 Forest와 addTree의 파라미터가 독립적으로 동작한다.
```kotlin
val forest = Forest<Birch>()
forest.addTree(Birch())
forest.addTree(Spruce())
```
이러한 상황을 의도하는 경우는 없을 것이고, 코드만봐서는 둘이 독립적으로 동작한다는 것은빠르게 알아내기 힘들다.
```kotlin
class Forest<T: Tree> {
    fun addTree(tree: T) {
        //...
    }
}

val forest = Forest<Birch>()
forest.addTree(Birch())
forest.addTree(Spruce()) // ERROR, type mismatch
```

만약 독립적으로 타입 파라미터를 의도했다면 이름을 아예 다르게 주는 것이 좋다.
```kotlin
class Forest<T: Tree> {
    fun <ST: T> addTree(tree: ST) {
        // ...
    }
}
```