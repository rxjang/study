# Observer Pattern
이번에는 기상 정보 스테이션 구축 프로젝트를 진헹하게 되었다. 이 시스템은 아래 3가지로 이루어진다.
* 기상 스테이션: 실제 기상 정보를 수집하는 장비
* WeatherData 객체: 기상 스테이션으로부터 오는 데이터를 추적하는 객체. 
* 디스플레이: 사용자에게 현재 기상 조건을 보여줌. 아래 항목이 갱신되면서 보여저야 함.
  * 현재 조건 (온도, 습도, 압력)
  * 기상 통계
  * 간단한 기상 예보
```kotlin
class WeatherData {
    
    fun getTemperature() {}

    fun getHumidity() {}
    
    fun getPressure() {}

    /**
     * 기상 관측값이 갱신될 때마다
     * 알려주기 위한 메소드
     */
    fun measurementsChanged() {}
}
```
우리가 해야할 일 또는 알고 있는 것은 다음과 같다. 
* WeathderData 클래스에는 세 가지 측장 값을 알아내기 위한 게터 메소드가 있다.
* 새로운 기상 측정 데이터가 나올 때마다 measurementsChanged() 메소드가 호출된다. 
* 기상 데이터를 사용하는 세 개의 디스플레이 항목을 구현해야 한다.
  * 현재 조건 표시
  * 기상 통계 표시
  * 기상 예보 표시
* 시스템이 확장 가능해야 한다.
  * 다른 개발자가 별도 디스플레이를 만들 수 있다.
  * 사용자들이 애플리케이션에 마음대로 디스플레이 항목을 추가/제거할 수 있다.  

옵저버 패턴에 대해 알아본 다음, 이를 적용해보자.

---
> **옵저버 패턴(Observer Pattern)** 에서는 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들한테 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다 의존성을 정의한다.   

옵저버 패턴을 구성하는 방법에는 여러가지가 있지만, 보통 주제 인터페이스와 옵저버 인터페이스가 들어있는 클래스 디자인을 바탕으로 한다.
* 주제 인터페이스: 상태를 저장하고 있는 객체
* 옵저버 인터페이스: 상태가 바뀔 때마다 바뀐 상태를 수신하고 그에 맞는 행동을 처리하는 객체  

일대다 관계는 주제와 옵저버에 의해 정의된다. 옵저버는 주제에 의존하고, 주제의 상태가 바뀌면 옵저버에게 연락이 간다.
연락 방법에 따라 옵저버에 있는 값이 새로운 값으로 갱신될 수도 있다. 옵저버 패턴에서는 주제와 옵저버가 **느슨하게 결합**되어 있는 객체 디자인을 제공한다.
> **느슨한 결합**은 그 둘이 상호작용을 하긴 하지만 서로에 대해 잘 모른다는 것을 의미한다. 
> 서로 상호작용을 하는 객체 사이에는 가능하면 느슨하게 결합하는 디자인을 사용해야 한다.  

* 주제가 옵저버에 대해서 아는 것은 옵저버가 특정 인터페이스를 구현한다는 것 뿐
* 옵저버는 언제든지 새로 추가할 수 있음
* 새로운 형식의 옵저버를 추가하려고 할 때도 주제를 전혀 변경할 필요가 없음
* 주제와 오저버는 서로 독립적으로 재사용할 수 있음
* 주제나 옵저버가 바뀌더라도 서로한테 영향을 미치지 않음

---
이제 이를 바탕으로 가상 스테이션 코드를 만들어보자.
```kotlin
interface Subject {
    fun registerObserver(o: Observer)
    fun removeObserver(o: Observer)
    fun notifyObservers()
}

interface Observer {
  fun update(temp: Float, humidity: Int, pressure: Int)
}

interface DisplayElement {
  fun display()
}

class WeatherData(
  var temperature: Float = 0.0f,
  var humidity: Float = 0.0f,
  var pressure: Float = 0.0f,
  private val observers: MutableList<Observer> = mutableListOf()
): Subject {

  init {
    measurementsChanged()
  }

  override fun registerObserver(o: Observer) {
    observers.add(o)
  }

  override fun removeObserver(o: Observer) {
    observers.remove(o)
  }

  override fun notifyObservers() {
    observers.forEach {
      it.update(temperature, humidity, pressure)
    }
  }

  fun measurementsChanged() {
    notifyObservers()
  }

  fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
    this.temperature = temperature
    this.humidity = humidity
    this.pressure = pressure
    measurementsChanged()
  }
}
```
디스플레이 항목도 만들어 보자.
```kotlin
class CurrentConditionsDisplay(
  private var temperature: Float,
  private var humidity: Float,
  private val weatherData: Subject,
): Observer, DisplayElement {

  constructor(weatherData: WeatherData) : this(weatherData.temperature, weatherData.humidity, weatherData) {
    weatherData.registerObserver(this)
  }

  override fun update(temperature: Float, humidity: Float, pressure: Float) {
    this.temperature = temperature
    this.humidity = humidity
    display()
  }

  override fun display() {
    println("current conditions: $temperature F degrees and $humidity % humidity")
  }
}
```
위 코드에서 찝찝한 두 부분을 집고 넘어가자.
* update() 메소드에서 display() 메소드를 호출하는 것이 최선인가?
  * 괜찮아 보이지만, 다른 방식으로 디자인 하는 것이 낫다. MVC 패턴을 배울 때 자세히 알아보자.
* Subject에 대한 래퍼런스는 왜 저장할까? 생성자 말고는 쓸데가 없지 않을까?
  * 그렇다. 하지만 나중에 옵저버 목록에서 탈퇴해야 할 때 주제 객체에 대한 래퍼런스를 저장해 두면 유용하게 쓸 수 있을 것이다. 
* 옵저버는 필요한 상황에서만 데이터를 가지고 오고 싶을 수 있지 않을까?
  * 그래서 push 방식과 pull 방식을 지원하는 경우도 있다. 
    * `push` 주제의 내용이 변경될 때마다 옵저버에게 알려주는 방식 
    * `PULL` 옵저버가 필요할 때마다 주제에게 정보를 요청하는 방식

---
## 자바에서 지원하는 옵저버 패턴 
java.util 패키지 내에는 옵저버 패턴을 지원하는 Observer 인터페이스와 Observable 클래스가 있다. 
이는 우리가 만들었던 인터페이스들과 꽤 비슷하지만 더 많은 기능을 제공한다. 그리고 푸시 방식으로 갱신할 수 있고, 풀 방식으로 갱신할 수도 있다.  
자바 버전은 다음과 같은 방식으로 쓰면 된다.

**객체에서 옵저버가 되는 방법** 

Observer 인터페이스를 구현하고 Observable 객체의 `addOberver()` 메소드를 호출하면된다. 
옵저버 목록에서 탈퇴하고 싶을 때는 `deleteObserver()`를 호출하면 된다.  

**Observable에서 연락을 돌리는 방법**  
Observable 수퍼클래스를 확장해 Observable 클래스를 만들어야 한다. 그리고 나서 두 단계를 더 거처야 한다.
1. `setChanged()` 매소드를 호출해서 객체의 상태가 바뀌었다는 것을 알린다.
   * 이 메소드는 상태가 바뀌었다는 것을 밝히기 위한 용도로 쓰인다. 나중에 `notifyObservers()`가 호출되었을 때 그 메소드에서 옵저버들을 갱신 시키게 된다.
2. 다음 중 하나의 메소드를 호출한다: `notifyObservers()` or `notifyObservers(Object arg)`

**옵저버가 연락을 받는 방법**  
전과 마찬가지로 update 메소드를 구현하지만 메소드 서명이 조금 다르다.  
`update(Observer o, Object arg)`  
* Observable: 연락을 보내는 주제 객체가 이 인자로 전달된다.
* Object: `notifyObservers()` 메소드에서 인자로 전달된 데이터 객체. 데이터 객체가 지정되지 않을 경우에는 null이 된다.

이제 이를 바탕으로 새롭게 구현해 보자.
```kotlin
class WeatherData(
    var temperature: Float = 0.0f,
    var humidity: Float = 0.0f,
    var pressure: Float = 0.0f,
): Observable() {
    
    fun measurementsChanged() {
        setChanged()
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measurementsChanged()
    }
    
}

class CurrentConditionsDisplay(
  private var temperature: Float,
  private var humidity: Float,
  private val weatherData: Observable,
): Observer, DisplayElement {

  constructor(weatherData: WeatherData) : this(weatherData.temperature, weatherData.humidity, weatherData) {
    weatherData.addObserver(this)
  }


  override fun display() {
    println("current conditions: $temperature F degrees and $humidity % humidity")
  }

  override fun update(o: Observable?, arg: Any?) {
    if (arg is WeatherData) {
      this.temperature = temperature
      this.humidity = humidity
      display()
    }
  }
}
```
## java.util.Observable의 단점
Observable은 구현이 아닌 인터페이스에 맞춰서 프로그래밍 해야한다는 디자인 원칙을 위배한다. 그렇다고 해서 쓸모 없는 것은 아니지만, 몇 가지 주의해야할 점들이 있다.
* Observable은 클래스이다. 
   * 이는 수퍼클래스이기 때문에 구현할 때 서브클래스로 만들어야 한다. 그렇다면 이미 다른 수퍼클래스를 확장하고 있는 클래스에 Observable 기능을 추가 할 수없어 재사용에 제약이 생긴다.
   * Observable 인터페이스가 없어 자바에 내장된 Observer APi 하고 잘 맞는 클래스를 직접 구현하는 것이 불가능하다. 
* Observable 클래스의 핵심 메소드를 외부에서 호출할 수 없다.  
  `setChanged()`가 protected 로 선언되어 있어, Observable의 서브 클래스에서만 이를 호출할 수 있다. 이는 디자인 상속보다는 구성을 사용한다는 디자인 원칙에도 위배된다.  

### 또한 java9 버전부터 deprecated
* Observer와 Observable이 제공하는 이벤트 모델이 제한적이고, Observable의 notify는 순서를 보장할 수 없음
* `java.beans` 패키지애서 더 풍부한 이벤트 모델을 제공하고 있음
* 멀티 스레드에서의 신뢰할 수 있고 순서가 보장된 메시징은 `java.util.concurrent` 패키지의 concurrent 자료 구조들 중 하나를 골라 쓰는 편이 나음
* reactive stream 스타일 프로그래밍은 `java.util.concurrent.Flow` API를 쓰기를 권함

