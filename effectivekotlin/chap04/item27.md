# item27. 변화로부터 코드를 보호하려면 추상화를 사용하라
함수와 클래스 등의 추상화로 실질적인 코드를 숨기면 사용자가 세부사항을 알지 못해도 괜찮다. 
그래서 이후에 코드를 원하는대로 수정할 수도 있다.  
이번 item에서는 추상화를 통해 변화로부터 코드를 보호하는 행위가 어떤 자유를 가져오는지 살표보자.

## 상수
리터럴은 아무것도 설명하지 않는다. 따라서 코드에서 반복적으로 등장할 때 문제가 된다. 이러한 리터럴을 상수 프로퍼티로 변경하면 해당 값에 의미 있는 이름을 붙일 수 있으며, 상수의 값을 변경해야 할 때 훨씬 쉽게 변경할 수 있다. 
```kotlin
fun isPasswordValid(text: String): Boolean {
    if (text.length < 7) return false
    // ...
}
```
여기서 숫자 7은 '비밀번호 최소 길이'를 나타내겠지만 이해하는 데 시간이 걸린다. 상수로 빼면 훨씬 쉽게 이해할 수 있을 것이다.
```kotlin
const val MIN_PASSWORD_LENGTH = 7
fun isPasswordValid(text: String): Boolean {
    if (text.length < MIN_PASSWORD_LENGTH) return false
}
```
이렇게 하면 '비밀전호의 초소 길이'를 변경하기도 쉽다. 함수의 내부 로직을 전혀 이해하지 못해도, 상수의 값만 변경하면 된다. 상수로 추출하면
* 이름을 붙일 수 있고
* 나중에 해당 값을 쉽게 변경할 수 있다. 

## 함수
애플리케이션 개발중 사용자에게 토스트 메시지를 자주 출력해야 하는 상황이 발생했다고 하자. 
```kotlin
Toast.makeText(this, message, Toast.LENGTH_LONG).show()
```
이렇게 많이 사용되는 알고리즘은 다음과 같이 간단한 확장 함수로 만들어서 사용할 수 있다.
```kotlin
fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_LONG 
) {
    Toast.makeText(this, message, duration).show()
}
// 사용
context.toast(message)
```
이렇게 일반적인 알고리즘을 추출하면, 토스트를 출력하는 코드를 항상 기억해 두지 않아도 괜찮다. 또한 이후에 토스트를 출력하는 방법이 변경되어도, 확장 함수 부분만 수정하면 되므로 유지보수성이 향상된다.  
만약 토스트가 아니라 스낵바라는 다른 형태의 방식으로 출력해야 한다면 어떻게 해야할까? 
다음과 같이 스낵바를 출력하는 확장 함수를 만들고, 기존의 Context.toast()를 Context.snackbar()로 한꺼번에 수정하면 된다. 
```kotlin
fun Context.snackbar(
    message: String, 
    length: Int = Toast.LENGTH_LONG
) {
    // ...
}
```
하지만 이런 해결방법은 좋지 않다. 내부적으로만 사용하더라도 함수의 이름을 직접 바꾸는 것은 위험할 수 있다. 다른 모듈이 이 함수에 의존하고 있따면 다른 모듈에 큰 문제가 발생할 것이다. 
또한 함수의 이름은 한번에 바꾸기 쉽지만 파라미터는 그렇지 않다. 스낵바를 출력하는 행위가 토스트의 필드에 영향을 받는 것은 좋지 않다. 다른 한편으로 스낵바의 enum으로 모든 것을 변경하는 것도 문제를 발생 시킬 수 있다. 
```kotlin
fun Context.snackbar(
    message: String, 
    duration: Int = Snackbar.LENGTH_LONG
) {
    // ...
}
```
메시지의 출력 밥법이 바뀔수 있다는 것을 알고 있다면, 이때부터 중요한 것은 메시지의 출력 방법이 아니라, 사용자에게 메시지를 출력하고 싶다는 의도자체이다. 따라서 메시지를 출력하는 더 추상적인 방법이 필요하다. 
```kotlin
fun Context.showMessage(
    message: String, 
    duration: MessageLegnth = MessageLength.LONG
) {
    val toastDuration = when(duration) {
        SHORT -> Length.LENGTH_SHORT
        LONG -> Length.LENGTH_LONG
    }
    Toast.makeText(this, message, toastDuration).show()
}

enum class MessageLength { SHORT, LONG }
```
가장 큰 변화는 이름이다. 그냥 레이블을 붙이는 방식의 변화이므로 큰 차이가 없어 보이지만, 이러한 관점은 컴파일러의 관점에서만 그렇다. 사람의 관점에서 이름이 바뀌면 큰 변화가 일어난 것이다.
함수는 추상화를 표현하는 수단이며, 함수 시그니처는 이 함수가 어떤 추상화를 표현하고 있는지 알려주므로 의미 있는 이름은 굉장히 중요하다. 

## 클래스
클래스를 통해 구현을 더 강력하게 추상화할 수 있다.
```kotlin
class MessageDisplay(val context: Context) {
    fun show(
        message: String,
        duration: MessageLength = MessageLength.LONG
    ) {
        val toastDuration = when(duration) {
            SHORT -> Length.LENGTH_SHORT
            LONG -> Length.LENGTH_LONG
        }
        Toast.makeText(this, message, toastDuration).show()
    }
}
enum class MessageLength { SHORT, LONG }

// 사용
val messageDisplay = MessageDisplay(context)
messageDisplay.show("Message")
```
클래스는 아래와 같은 이유로 함수보다 더 강력하다.
* 상태를 가질 수 있음
* 많은 함수를 가질 수 있음  

## 인터페이스
인터페이스 뒤에 클래스를 숨기면 더 많은 자유를 얻으면서 더 추상적이게 만들 수 있다. 코틀린 표준 라이브러리를 보면 거의 모든 것이 인터페이스로 표현된다는 것을 알 수 있다. 
* listOf 함수는 List를 리턴한다. 여기서 List는 인터페이스이다. listOf는 팩토리 메서드라고 할 수 있다.
* 컬렉션 처리 함수는 Iterable 또는 Collection의 확장 함스로서, List, Map등을 리턴한다. 이것들은 모두 인터페이스이다.
* 프로퍼티 위임은 ReadOnlyProperty 또는 ReadWriteProperty 뒤에 숨겨진다. 이것들도 모두 인터페이스이다. 실질적인 클래스닌 일반적으로 private이다.   

라이브러리를 만드는 사람은 내부 클래스의 가시성을 제한하고 인터페이스를 통해 이를 노출하는 코드를 많이 사용한다. 이렇게 하면 사용자가 클래스를 직접 사용하지 못하므로 라이브러리를 만드는 사람은 인터페이스만 유지한다면 별도의 걱정 없이 자신이 원하는 형태로 그 구현을 변경할 수 있다.  
메시지 표시 예제에 인터페이스도 도입해 보자. 
```kotlin
interface MessageDisplay {
    fun show(message: String, duration: MessageLength = LONG)
}

class ToastDisplay(val context: Context): MessageDisplay {
    override fun show(
        message: String,
        duration: MessageLength
    ) {
        val toastDuration = when (duration) {
            SHORT -> Length.SHORT
            LONG -> Length.LONG
        }
        Toast.makeText(context, message, toastDuration).show()
    }
}

enum class MessageLength { SHORT, LONG }
```
이렇게 구상하면 더 많은 자유를 얻을 수 있다. 
* 공통 모듈에서도 MessageDisplay를 사용할 수 있다. 플랫폼 별로 구현만 다르게 하면된다.
* 인터페이스 페에킹이 클래스 모킹보다 간단하므로 별도의 모킹 라이브러리를 사용하지 않아도 된다.
* 선언과 사용이 분리되어 ToastDisplay 등의 실제 클래스를 자유롭게 변경할 수 있다.

## ID 만들기 (nextId)
프로젝트에서 고유 ID를 사용해야하는 상황에서의 코드를 작성해보자.
```kotlin
var nextId: Int = 0
val newId = nextId++
```
위의 코드는 위험한 코드이다. 
* 이 코드의 ID는 무조건 0부터 시작한다. 
* 이 코드는 스레드 안전하지 않다.  

그래도 이 방법을 사용해야 한다면 이후 발생할 수 있는 변경으로부터 코드를 보호할 수 있게 함수를 사용하는 것이 좋다.
```kotlin
private var nextId: Int = 0
fun getNextId(): Int = nextId++
```
이제 ID 생성 방식의 변경으로부터는 보호되지만, ID 타입 변경 등은 대응하지 못한다. 미래 어느 시점에 ID를 문자열로 변경해야 한다면 어떨까?
이를 방지하려면 이후에 ID 타입을 쉽게 변경할 수 있게 클래스를 사용한는 것이 좋다.
```kotlin
data class Id(private id: Int)

private var nextId: Int = 0
fun getNextId():Id = Id(nextId++)
```
더 많은 추상화는 더 많은 자유를 주지만, 이를 정의하고 사용하고 이해하는것이 조금 어려워졌다.

## 추상화가 주는 자유
지금 까지의 추상화 방법을 정리하면 다음과 같다.
* 상수로 추출한다.
* 동작을 함수로 래핑한다.
* 함수를 클래스로 래핑한다.
* 인터페이스 뒤에 클래스를 숨긴다.
* 보편적인 객체를 특수한 객체로 래핑한다.  

이를 구현 할때는 다음과 같은 도구를 사용할 수 있다. 
* 제네릭 타입 파라미터를 사용한다.
* 내부 클래스를 추출한다.
* 생성을 제한한다.

## 추상화의 문제
추상화를 하려면 코드를 읽는 사람이 해당 개념을 배우고 잘 이해해야한다. 추상화에는 비용이 발생하므로 극단적으로 모든 것을 추상화해서는 안된다.  
추상화는 거의 무한하게 할 수 있지만 어느 순간부터는 득보다 실이 많아질 것이다.  
추상화는 많은 것을 숨길 수 있는 테크닉이다. 생각할 것을 어느 정도 숨겨야 개발이 쉬워지는 것도 사실이지만 너무 많은 것을 숨기면 결과를 이애하는 것 자체가 어려워진다. 

## 어떻게 균형을 맞춰야 할까?
프로젝트에 따라 균형이 다를 수 있다. 다양한 요소에 따라 그 균형이 다르지만, 그래도 사용할 수 있는 규칙을 정리해 보면 다음과 같다. 
* 많은 개발자가 참여하는 프로젝트는 이후에 객체 생성과 사용 방법을 변경하기 어렵다. 따라서 추상화 방법을 사용하는 것이 좋다. 최대한 모듈과 부분을 분리하는 것이 좋다. 
* 의존성 주입 프레임워크를 사용하면 생성이 얼마나 복잡한지는 신경 쓰지 않아도된다. 클래스 등은 한번만 정의하면 된다.
* 테스트를 하거나 다른 애플리케이션 기반으로 새로운 애플리케이션을 만든다면 추상화를 사용하는 것이 좋다.
* 프로젝트가 작고 실험적이라면, 추상화를 하지 않고도 직접 변경해도 괜찮다. 문제가 발생했다면 최대한 빨리 직접 변경하자.  

## 정리
추상화는 단순하게 중복성을 제거해서 코드를 구성하기 위한 것이 아니다. 추상화는 코드를 변경해야 할 때 도움이 된다. 
추상화를 사용하는 것은 어렵지만, 이를 배우고 이해해야 한다. 
추상화를 사용할 때의 장점과 단점을 모두 이해하고, 프로젝트 내에서 그 균형을 찾아야 한다. 
추상화가 너무 많거나 적은 상황 모두 좋은 상황이 아니다. 