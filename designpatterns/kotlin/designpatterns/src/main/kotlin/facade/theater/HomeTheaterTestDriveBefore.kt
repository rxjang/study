package facade.theater

class HomeTheaterTestDriveBefore {

    fun main() {
        val popper = PopcornPopper()
        val lights = TheaterLights()
        val screen = Screen()
        val projector = Projector()
        val dvd = DvdPlayer()
        val amp = Amplifier()

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
    }
}