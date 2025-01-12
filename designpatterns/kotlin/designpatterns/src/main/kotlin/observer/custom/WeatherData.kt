package observer.custom

class WeatherData(
    var temperature: Float = 0.0f,
    var humidity: Float = 0.0f,
    var pressure: Float = 0.0f,
    private val observers: MutableList<Observer> = mutableListOf()
): Subject {

    init {
        measurementsChanged()
    }

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        observers.remove(o)
    }

    override fun notifyObservers() {
        observers.forEach {
            it.update(temperature, humidity, pressure)
        }
    }

    fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measurementsChanged()
    }
}