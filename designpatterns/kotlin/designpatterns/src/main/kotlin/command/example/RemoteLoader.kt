package command.example

fun main() {
    val light = Light()
    val lightOnCommand = LightOnCommand(light)
    val lightOffCommand = LightOffCommand(light)
    val garageDoor = GarageDoor()
    val garageDoorOpenCommand = GarageDoorOpenCommand(garageDoor)
    val garageDoorCloseCommand = GaragedDoorCloseCommand(garageDoor)

    val onCommand = listOf(lightOnCommand, garageDoorOpenCommand)
    val offCommand = listOf(lightOffCommand, garageDoorCloseCommand)

    val remoteControl = RemoteControl(slotTotal = 2, onCommand = onCommand, offCommand = offCommand)

    remoteControl.onButtonWasPressed(0)
    remoteControl.offButtonWasPressed(0)
    remoteControl.undoButtonWasPushed()
    remoteControl.onButtonWasPressed(1)
    remoteControl.offButtonWasPressed(1)
    remoteControl.undoButtonWasPushed()
}