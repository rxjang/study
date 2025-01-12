package observer.fromjava

import java.util.*

class CurrentConditionsDisplay(
    private var temperature: Float,
    private var humidity: Float,
    private val weatherData: Observable,
): Observer, DisplayElement {

    constructor(weatherData: WeatherData) : this(weatherData.temperature, weatherData.humidity, weatherData) {
        weatherData.addObserver(this)
    }


    override fun display() {
        println("current conditions: $temperature F degrees and $humidity % humidity")
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is WeatherData) {
            this.temperature = o.temperature
            this.humidity = o.humidity
            display()
        }
    }
}