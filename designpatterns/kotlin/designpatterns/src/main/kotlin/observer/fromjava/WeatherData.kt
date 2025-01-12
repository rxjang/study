package observer.fromjava

import java.util.Observable

class WeatherData(
    var temperature: Float = 0.0f,
    var humidity: Float = 0.0f,
    var pressure: Float = 0.0f,
): Observable() {

    fun measurementsChanged() {
        setChanged()
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measurementsChanged()
    }

}