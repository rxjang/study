package observer.custom

fun main() {
    val weatherData = WeatherData()
    val currentConditionsDisplay = CurrentConditionsDisplay(weatherData)
    weatherData.setMeasurements(80f, 65f, 30.4f)
}