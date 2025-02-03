package factory

import java.time.Period

abstract class Pizza(
    val name: String = "pizza",
    val dough: Dough = Dough(),
    val sauce: Sauce = Sauce(),
    val cheese: Cheese = Cheese(),
    val pepperoni: Pepperoni = Pepperoni(),
    val veggies: List<Veggies> = listOf(Veggies()),
    val clams: Clams = Clams(),
) {

    open fun prepare() {
        println("preparing $name")
        println("Tossing dough...")
    }

    fun bake() {}

    fun cut() {}

    fun box() {}
}

class CheesePizza(
    private val pizzaIngredientFactory: PizzaIngredientFactory,
): Pizza(
    name = "cheesePizza",
    dough = pizzaIngredientFactory.createDough(),
    sauce = pizzaIngredientFactory.createSauce(),
    cheese = pizzaIngredientFactory.createCheese()
)

class PepperoniPizza(
    private val pizzaIngredientFactory: PizzaIngredientFactory,
): Pizza(
    name = "PepperoniPizza",
    dough = pizzaIngredientFactory.createDough(),
    sauce = pizzaIngredientFactory.createSauce(),
    cheese = pizzaIngredientFactory.createCheese(),
)

class GreekPizza(
    private val pizzaIngredientFactory: PizzaIngredientFactory,
): Pizza(
    name = "GreekPizza",
    dough = pizzaIngredientFactory.createDough(),
    sauce = pizzaIngredientFactory.createSauce(),
    cheese = pizzaIngredientFactory.createCheese(),
)


class ChicagoStyleCheesePizza: Pizza()
class ChicagoStylePepperoniPizza: Pizza()
class ChicagoStyleGreekPizza: Pizza()