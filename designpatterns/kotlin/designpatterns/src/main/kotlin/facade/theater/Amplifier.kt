package facade.theater

class Amplifier(
    private val tuner: Tuner = Tuner(),
    private var dvdPlayer: DvdPlayer = DvdPlayer(),
    private val cdPlayer: CdPlayer = CdPlayer(),
) {

    fun on() {}
    fun off() {}
    fun setCd() {}
    fun setDvd(dvd: DvdPlayer) {
        this.dvdPlayer = dvd
    }
    fun setStereoSound() {}
    fun setSurroundSound() {}
    fun setTuner() {}
    fun setVolume(volume: Int) {}
}