package factory

interface PizzaIngredientFactory {

    fun createDough(): Dough
    fun createSauce(): Sauce
    fun createCheese(): Cheese
    fun createVeggies(): List<Veggies>
    fun createClam(): Clams
}

class NYPizzaIngredientFactory: PizzaIngredientFactory {
    override fun createDough(): Dough {
        return Dough()
    }

    override fun createSauce(): Sauce {
        return Sauce()
    }

    override fun createCheese(): Cheese {
        return Cheese()
    }

    override fun createVeggies(): List<Veggies> {
        return listOf(Veggies())
    }

    override fun createClam(): Clams {
        return Clams()
    }
}