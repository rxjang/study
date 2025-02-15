package command.example

class GarageDoor {

    fun up() {
        println("Garage door is up")
    }

    fun down() {
        println("Garage door is down")
    }

    fun stop() {}

    fun lightOn() {}

    fun lightOff() {}

}

class GarageDoorOpenCommand(
    val garageDoor: GarageDoor,
): Command {
    override fun execute() {
        garageDoor.up()
    }

    override fun undo() {
        garageDoor.down()
    }
}

class GaragedDoorCloseCommand(
    val garageDoor: GarageDoor
): Command {
    override fun execute() {
        garageDoor.down()
    }

    override fun undo() {
        garageDoor.up()
    }

}