# Singleton Pattern
싱글턴 패턴은 인스턴스가 하나 뿐인 특별한 객체를 만들 수 있게 해주는 패턴이다. 클래스가 하나여서 간단해 보이나, 구현하기는 보이는 만큽 쉽지 않다. 

> 싱글턴 패턴은 해당 클래스의 인스턴스가 하나만 만들어지고, 어디서든지 그 인스턴스에 접근할 수 있도록 하기 위한 패턴이다.

## 왜 싱글턴 패턴을 사용하는가?
* 하나만 존재해야 하는 객체를 만들 때 사용한다.
  * 스레드 풀, 캐시, 대화상자, etc...
  * 이러한 것들은 두 개 이상 만들시 프로그램이 이상하게 돌아갈 수 있다.
* 싱글턴 패턴을 사용하면 전역 변수를 사용할 때와 마찬가지로 객체 인스턴스를 어디서든지 엑세스 할 수 있다.
  * 하지만 전역 변수 처럼 단점을 감수해야하는 것은 아니다. 싱글턴 패턴은 필요할 때만 객체를 만들 수 있기 때문이다.
* 싱글턴 패턴은 어떻게 하면 한 클래스의 인스턴스가 두 개 이상이 만들어지지 않도록 할 수 있게 하는지가 중요하다.

## 고전적인 싱글턴 패턴
다음은 고전적인 싱글턴 패턴을 만드는 예시이다. 다만 이 코드에는 문제가 있으니 사용에 주의하자. 
```kotlin
// 생성자를 private으로 선언했기 때문에 Sigleton 안에서만 인스턴스를 만들 수 있음
class Singleton private constructor() {

    companion object {
        // 유일한 인스턴스를 저장하기 위한 정적 변수
       // null일시 아직 인스턴스가 생성되지 않은 것
        private var uniqueInstance: Singleton? = null

        fun getInstance(): Singleton {
            return uniqueInstance ?: Singleton()
        }
    }
}
```

## 초콜릿 공장 예시
초콜릿 공장에서 초콜릿 보일러를 컴퓨터로 제어해 초콜릿을 만든다.
```kotlin
class ChocolateBoiler private constructor(
  private var empty: Boolean = true,
  private var boiled: Boolean = false,
) {

  companion object {
    private var uniqueInstance: ChocolateBoiler? = null

    fun getInstance(): ChocolateBoiler {
      return uniqueInstance ?: ChocolateBoiler()
    }
  }

  fun fill() {
    if (isEmpty()) {
      empty = false
      boiled = false
    }
  }

  fun drain() {
    if (!isEmpty() && isEmpty()) {
      empty = true
    }
  }

  fun boil() {
    if (!isEmpty() && !isBoiled()) {
      boiled = true
    }
  }

  fun isEmpty(): Boolean {
    return empty
  }

  fun isBoiled(): Boolean {
    return boiled
  }
}
```
### 문제 상황
초콜렛 보일러에 문제가 생겼다. fill()에서 아직 초콜릿이 끓고 있는데 재료를 집어 넣은 것이다. 왜 이런일이 발생한 걸까? 
첫 번째 스레드에서 unqiueInstance가 null인것을 확인하고 인스턴스를 만들기 전 찰나의 순간에 두 번째 스레드가 unqiueInstance의 null여부를 확인한 것이다. 
결국 두 개의 인스턴스가 만들어지게 되었다. 

### 해결1
getInstance()를 해결해 보자.

```kotlin
class Singleton private constructor() {

    companion object {
        private var uniqueInstance: Singleton? = null

        fun getInstance(): Singleton {
            return synchronized(this) {
                uniqueInstance ?: Singleton()
            }
        }
    }
}
```
synchronized를 통해 한 스레드의 메소드가 사용을 끝나기 전까지 다른 메소드는 기다려야한다. 두 스레드가 이 메소드를 동시에 실행하는 일은 없을 것이다. 
다만, 이렇게 하면 속도 문제가 생길 수 있다. 동기화가 필요한 시점은 이 메소드가 시작되는 때 뿐이기 때문이다. 첫 번째 과정을 제외하면 동기화는 불필요한 오버헤드만 증가 시킨다.

## 해결2
1. getInstance()의 속도가 중요하지 않다면 그냥 둔다.
2. 인스턴스를 필요할 때 생성하지말고, 처름부터 만들어 버린다. 
3. DCL(Double-Checking Locking)을 써서 getInstance()에서 동기화되는 부분을 줄인다.
* Volatile은 변수를 main memory에 저장하겠다는 것을 명시한다.
* 매번 변수을 읽을 때마다 CPU cache에 저장된 값이 아닌 메인 메모리에서 읽는다.
* 이로 인해 멀티 쓰레드 환경에서 메인 메모리 값을 참조하므로 변수 값 불일치 문제를 해결할 수 있다. 
```kotlin
class DCLSingleton private constructor() {

    companion object {
        @Volatile
        private var uniqueInstance: DCLSingleton? = null

        fun getInstance(): DCLSingleton {
            return synchronized(this) {
                uniqueInstance ?: DCLSingleton()
            }
        }
    }
}
```


