package observer.custom

interface Observer {
    fun update(temperature: Float, humidity: Float, pressure: Float)
}