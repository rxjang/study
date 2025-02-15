package command.example

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