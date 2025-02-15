package factory

open class SimplePizzaFactory {

//    fun createPizza(type: String): Pizza {
//        return when (type) {
//            "cheese" -> CheesePizza()
//            "greek" -> GreekPizza()
//            "pepperoni" -> PepperoniPizza()
//            else -> throw RuntimeException()
//        }
//    }
}

class NYPizzaFactory: SimplePizzaFactory()

class ChicagoPizzaFactory: SimplePizzaFactory()
