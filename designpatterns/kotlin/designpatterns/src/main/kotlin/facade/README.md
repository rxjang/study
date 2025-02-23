# Facade Pattern
> 퍼사드 패턴은 어떤 서브시스템의 일련의 인터페이스에 대한 통합된 인터페이스를 제공한다. 
> 퍼사드에서 고수준 인터페이스를 정희하기 때문에 서브시스템을 더 쉽게 사용할 수 있다.  

## 예제
홈 씨어터를 구축하기 위해 DVD 플레이어, 프로젝터, 자동 스크린, 서라운드 음향 및 팝콘 기계까지 갖춘 시스템을 구성했다. 
영화를 보기 위해선 아래와 같은 일을 해야 한다.
```kotlin
// 1. 팝콘 기계를 켠다
popper.on()
// 2. 팝콘 튀기기 시작
popper.pop()
// 3. 전등을 어둡게 조절
lights.dim(10)
// 4. 스크린 내리기
screen.down()
// 5. 프로젝터 켜기
projector.on()
// 6. 프로젝터로 DVD 신호가 입력되도록 하기
projector.setInput(dvd)
// 7. 프로젝터를 와이드 스크린 모드로 전환
projector.wideScreenMode()
// 8. 앱프를 켠다
amp.on()
// 9. 앰프 입력을 DVD로 전환한다.
amp.setDvd(dvd)
// 10. 앰프를 서라운드 음향 모드로 전환한다.
amp.setSurroundSound()
// 11. 앰프 볼륨을 중간으로 설정한다.
amp.setVolume(5)
// 12. DVD플레이어를 켠다.
dvd.on()
// 13. DVD를 재생한다.
dvd.play("Harry Potter")
```
여기서 해야할 일이 더 생긴다면 사용법이 더 복잡해 질 것이다. 
파사트 패턴을 작용해서 복잡한 일을 간단하게 처리하는 방법을 알아보자. 

### 퍼사드 패턴 적용
퍼사드 패턴을 쓰면 훨씬 쓰기 쉬운 인터페이스를 제공하는 퍼사드 클래스를 구현함으로써 복잡한 시스템을 쉽게 사용할 수 있다.
1. 홈 씨어터 시스템용 퍼사드를 만들자. watchMovie() 같이 몇 가지 간단한 메소드만 들어있는 HomeTheaterFacade라는 클래스를 만들자.
2. 퍼사드 클래스에서는 홈 씨어터 구성요소들을 하나의 서브시스템으로 간주하고, watchMovie()메소드에서는 서브시스템의 메소드들을 호출해 필요한 작업을 처리한다. 
3. 이제 클라이언트 코드에서는 서브시스템이 아닌 홈씨어터 파사트에 있는 메서드를 호출한다. 
4. 퍼사드를 쓰다라도 서브시스템에는 여전히 직접 접근할 수 있다. 서브시스템의 고급기능이 필요하다면 언제든지 마음대로 쓸 수 있다.
```kotlin
class HomeTheaterFacade(
    private val amp: Amplifier,
    private val tuner: Tuner,
    private val dvd: DvdPlayer,
    private val cd: CdPlayer,
    private val projector: Projector,
    private val lights: TheaterLights,
    private val screen: Screen,
    private val popper: PopcornPopper,
) {

    fun watchMovie(movie: String) {
        popper.on()
        popper.pop()
        lights.dim(10)
        screen.down()
        projector.on()
        projector.setInput(dvd)
        projector.wideScreenMode()
        amp.on()
        amp.setDvd(dvd)
        amp.setSurroundSound()
        amp.setVolume(5)
        dvd.on()
        dvd.play(movie)
    }

    fun endMovie() {
        popper.off()
        lights.on()
        screen.up()
        projector.off()
        amp.off()
        dvd.stop()
        dvd.eject()
        dvd.off()
    }
}

class HomeTheaterTestDrive {

    fun main() {
        val popper = PopcornPopper()
        val tuner = Tuner()
        val lights = TheaterLights()
        val screen = Screen()
        val projector = Projector()
        val cd = CdPlayer()
        val dvd = DvdPlayer()
        val amp = Amplifier()

        val homeTheater = HomeTheaterFacade(amp, tuner, dvd, cd, projector, lights, screen, popper)
        homeTheater.watchMovie("Harry Potter")
        homeTheater.endMovie()
    }
}
```
이렇게 구현한 퍼사드 패턴은 다금과 같은 특징이 있다.
* 퍼사드 클래스에서는 서비시스템 클래스들을 탭슐화하지 않는다. 그냥 서브시스템의 기능을 사용할 수 있는 간단한 인터페이스를 제공할 뿐이다. 
* 특정 서브시스템에 대해 만들 수 있는 퍼사트 클래스에는 제한이 없다.
* 퍼사드 패턴을 사용하면 클라이언트 구현과 서브시스템을 분리시킬 수 있다. 기능이 추가된다면 클라이언트 코드는 고칠 필요 없이 퍼사드만 바꾸면 된다.
* 퍼사드와 어댑터는 모두 여러 개의 클래스를 감쌀 수 있다. 하지만 퍼사드는 인터페이스를 단순화시키기 위한 용도로 쓰이는 방면, 어댑터는 인터페이스를 다른 인터페이스로 변환하시 위한 용도로 쓰인다.

## 최소 지식 원칙
> 최소 지식 원칙: 정말 친한 친구하고만 얘기하라
* 객체 사이의 상호작용은 될 수 있으면 아주 가까운 '친구'사에아서만 허용하자.
* 이 원칙을 따르면 여러 클래스들이 복잡하게 얽혀서 시스템의 한 부분을 변경 했을 떄 다른 부분까지 줄줄이 고쳐야 하는 상황을 방지 할 수 있다.

이 원칙을 지키려면 다음과 같은 가이드라인을 따르면 된다. 어떤 메소드에서든지 다음 네 종류의 객체의 메소드만 호출하면된다.
1. 객체 자체
2. 메소드에 매개변수로 전달된 객체
3. 그 메소드에서 생성하거나 인스턴스를 만든 객체
4. 그 객체에 속하는 구성요소

```kotlin
// 원칙을 따르지 않은 경우
fun getTemp(): Float {
    return station.getThermometer().getTemperature()
}
// 원칙을 따른 경우
fun getTemp(): Float {
    return station.getTemperature()
}
```