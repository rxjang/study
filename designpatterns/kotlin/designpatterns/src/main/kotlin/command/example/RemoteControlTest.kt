package command.example


fun main() {
    val light = Light()
    val lightOn = LightOnCommand(light)
    val remoteControl = SimpleRemoteControl(lightOn)

    remoteControl.buttonWasPressed()

    val garageDoorOpenCommand = GarageDoorOpenCommand(GarageDoor())
    remoteControl.setCommand(garageDoorOpenCommand)
    remoteControl.buttonWasPressed()
}
