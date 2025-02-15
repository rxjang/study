# Command Pattern
이번 장에서는 한 차원 높은 단계의 캡슐화를 배워보자. 메소드 호출을 캡슐화 하는 것이다.

> 커맨드 패턴을 이용하면 요구사항을 객체로 캡슐화 할 수 있으며, 매개변수를 써서 여러 가지 다른 요구 사항을 집어 넣을수도 있다. 
> 또한 요청 내역을 큐에 저장하거나 로그로 기록할 수도 있으며, 작업취소 기능도 지원한다. 

커맨드 패턴에는 크게 클라이언트, 리시버, 커맨드, 인보커가 존재한다. 
1. 클리이언트에서 커맨드 객체를 생성한다. -> `createCommandObject()`
2. `setCommand()` 를 호출하여 인보커에 커맨드 객체를 저장한다. 
3. 나중에 클라이언트에서 인보커한테 그 명령을 실행시켜달라는 요청을 한다. -> `execute()`

* 클라이언트는 커맨드 객체를 생성해야 한다. 커맨드 객체는 리시버에 전달할 일련의 행동으로 구성된다. 
* 커맨드 객체에는 행동과 리시버에 대한 정보가 같이 들어있다. 
* 커맨드 객체에서 제공하는 메소드는 `execute()` 하나 뿐이다. 이 메소드에서는 여러 행동을 캡슐화 하며, 리시버에 있는 특정해동을 처리하기 위한 메소드를 호출하기 위한 메소드이다. 
* 클라이언트에서는 인보커 객체의 `setCommand()` 메소드를 호출하는데 어떤 커맨드 객체를 넘겨줍니다. 그 커맨드 객체는 나중에 쓰이기 전까지 인보커 객체에 보관된다. 
* 인보커에서 커맨드 객체의 `execute()` 메소드를 호출하면 리시버에 있는 특정 행동을 하는 메소드가 호출된다. 

## 리모컨 예제
리모컨에 일곱 가지 프로그래밍이 가능한 슬롯이 있다. 각 슬롯 마다 on과 off버튼이 있다. 
그리고 누른 버튼에 대한 명령을 취소하기 위한 UNDO 버튼이 존재한다.  

모든 클래스에 on()하고 off() 메소드만 있으면 될 것이라고 예상했는데, dim(), setTemperature()등의 메소드들이 있다. 
게다가 다른 제품이 추가되면 또다른 메소드가 추가될 수도 있다.

커맨드 패턴을 쓰면 이러한 요구 사항을 해결할 수 있다. **커맨드 객체는 특정 객체에 대한 특정 요청을 캡슐화 시켜 준다.**  
```kotlin
interface Command {
    fun execute()
}

class LightOnCommand(
    val light: Light,
): Command {
    override fun execute() {
        light.on()
    }
}

class SimpleRemoteControl(
    slot: Command
) {

    private var slot = slot

    fun setCommand(command: Command) {
        slot = command
    }

    fun buttonWasPressed() {
        slot.execute()
    }
}

fun main() {
    val light = Light()
    val lightOn = LightOnCommand(light)
    val remoteControl = SimpleRemoteControl(lightOn)

    remoteControl.buttonWasPressed()
}
```
이제 어느정도 리모컨을 만들수 있는 골격이 만들어 졌다. 리모칸의 각 슬롯에 명령을 할당해 보자. 

```kotlin
class RemoteControl(
    val slotTotal: Int,
    val onCommand: List<Command>,
    val offCommand: List<Command>,
) {

    init {
        if (onCommand.size > slotTotal || offCommand.size > slotTotal) {
            throw RuntimeException("슬롯 할당 갯수를 초과했습니다.")
        }
    }

    fun onButtonWasPressed(slot: Int) {
        onCommand[slot].execute()
    }

    fun offButtonWasPressed(slot: Int) {
        offCommand[slot].execute()
    }

}

fun main() {
    val light = Light()
    val lightOnCommand = LightOnCommand(light)
    val lightOffCommand = LightOnCommand(light)
    val garageDoor = GarageDoor()
    val garageDoorOpenCommand = GarageDoorOpenCommand(garageDoor)
    val garageDoorCloseCommand = GaragedDoorCloseCommand(garageDoor)

    val onCommand = listOf(lightOnCommand, garageDoorOpenCommand)
    val offCommand = listOf(lightOffCommand, garageDoorCloseCommand)

    val remoteControl = RemoteControl(slotTotal = 2, onCommand = onCommand, offCommand = offCommand)

    remoteControl.onButtonWasPressed(0)
    remoteControl.offButtonWasPressed(0)
    remoteControl.onButtonWasPressed(1)
    remoteControl.offButtonWasPressed(1)
}
```
### UNDO 버튼 지원
1. 커맨드 클래스에 undo() 메소드를 추가하자.
2. Undo()는 execute()의 반대로 동작을 시키자.
3. RemoteControl에는 마지막으로 실행된 명령을 기록하기 위한 인스턴스 변수를 추가하고 UNDO버튼을 누르면 레퍼런스를 호출하자.
```kotlin
class LightOnCommand(
    val light: Light,
): Command {
    override fun execute() {
        light.on()
    }

    override fun undo() {
        light.off()
    }
}

data class RemoteControl(
    private val slotTotal: Int,
    private val onCommand: List<Command>,
    private val offCommand: List<Command>,
    private var undoCommand: Command? = null,
) {

    init {
        if (onCommand.size > slotTotal || offCommand.size > slotTotal) {
            throw RuntimeException("슬롯 할당 갯수를 초과했습니다.")
        }
    }

    fun onButtonWasPressed(slot: Int) {
        onCommand[slot].execute()
        undoCommand = onCommand[slot]
    }

    fun offButtonWasPressed(slot: Int) {
        offCommand[slot].execute()
        undoCommand = offCommand[slot]
    }

    fun undoButtonWasPushed() {
        undoCommand?.undo()
    }

}
```
