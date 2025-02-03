package factory

abstract class PizzaStore {

    abstract fun createPizza(type: String): Pizza

    fun orderPizza(type: String): Pizza {
        val pizza = createPizza(type)

        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()
        return pizza
    }
}

class NYPizzaStore: PizzaStore() {
    override fun createPizza(type: String): Pizza {
        val ingredientFactory = NYPizzaIngredientFactory()
        return when (type) {
            "cheese" -> CheesePizza(ingredientFactory)
            "greek" -> GreekPizza(ingredientFactory)
            "pepperoni" -> PepperoniPizza(ingredientFactory)
            else -> throw RuntimeException()
        }
    }
}

class ChicagoPizzaStore: PizzaStore() {
    override fun createPizza(type: String): Pizza {
        return when (type) {
            "cheese" -> ChicagoStyleCheesePizza()
            "greek" -> ChicagoStyleGreekPizza()
            "pepperoni" -> ChicagoStylePepperoniPizza()
            else -> throw RuntimeException()
        }
    }
}
