package observer.custom

class CurrentConditionsDisplay(
    private var temperature: Float,
    private var humidity: Float,
    private val weatherData: Subject,
): Observer, DisplayElement {

    constructor(weatherData: WeatherData) : this(weatherData.temperature, weatherData.humidity, weatherData) {
        weatherData.registerObserver(this)
    }

    override fun update(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        display()
    }

    override fun display() {
        println("current conditions: $temperature F degrees and $humidity % humidity")
    }
}