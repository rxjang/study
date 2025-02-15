package command.example

class Streo {
    private var volume = 0

    fun on() {
    }

    fun off() {

    }

    fun setCd() {

    }

    fun setDvd() {

    }

    fun setRadio() {

    }

    fun setVolume(volume: Int) {
        this.volume = volume
    }
}

class StereoOnWithCDCommand(
    val streo: Streo,
): Command {
    override fun execute() {
        streo.on()
        streo.setCd()
        streo.setVolume(11)
    }

    override fun undo() {
        streo.off()
    }

}