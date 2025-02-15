package command.example

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