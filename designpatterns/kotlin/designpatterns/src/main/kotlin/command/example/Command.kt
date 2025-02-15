package command.example

interface Command {
    fun execute()
    fun undo()
}