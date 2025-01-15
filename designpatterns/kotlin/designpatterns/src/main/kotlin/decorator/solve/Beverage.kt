package decorator.solve

abstract class Beverage(
    open val description: String
) {
    abstract fun cost(): Int
}

class HouseBlend(): Beverage("HouseBlend") {
    override fun cost(): Int = 1_000
}

class DarkRoast(): Beverage("DarkRoast") {
    override fun cost(): Int = 2_000
}