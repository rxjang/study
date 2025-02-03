package factory

class PizzaTestDrive {

    fun main() {
        val nyStore = NYPizzaStore()
        nyStore.orderPizza("greek")

        val chicagoStore = ChicagoPizzaStore()
        chicagoStore.orderPizza("greek")
    }
}