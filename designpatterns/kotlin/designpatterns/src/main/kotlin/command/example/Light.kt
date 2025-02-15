package command.example

class Light {

    fun on() {
        println("Light is on")
    }

    fun off() {
        println("Light is off")
    }
}

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

class LightOffCommand(
    val light: Light,
): Command {
    override fun execute() {
        light.off()
    }

    override fun undo() {
        light.on()
    }

}