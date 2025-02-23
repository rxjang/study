package facade.theater

class HomeTheaterTestDriveAfter {

    fun main() {
        val popper = PopcornPopper()
        val tuner = Tuner()
        val lights = TheaterLights()
        val screen = Screen()
        val projector = Projector()
        val cd = CdPlayer()
        val dvd = DvdPlayer()
        val amp = Amplifier()

        val homeTheater = HomeTheaterFacade(amp, tuner, dvd, cd, projector, lights, screen, popper)
        homeTheater.watchMovie("Harry Potter")
        homeTheater.endMovie()
    }
}