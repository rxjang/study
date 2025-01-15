package decorator.solve

abstract class CondimentDecorator(
    override val description: String,
): Beverage(description) {
}

class Whip(
    val beverage: Beverage,
): CondimentDecorator("${beverage.description} Whip") {

    override fun cost(): Int {
        return 500 + beverage.cost()
    }
}

class Soy(
    val beverage: Beverage,
): CondimentDecorator("${beverage.description} Soy") {

    override fun cost(): Int {
        return 1_000 + beverage.cost()
    }
}