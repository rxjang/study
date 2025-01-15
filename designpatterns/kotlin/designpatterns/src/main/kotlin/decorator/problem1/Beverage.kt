package decorator.problem1

abstract class Beverage(
    val description: String
) {

    abstract fun cost(): Int
}

class HouseBlend(): Beverage("HouseBlend") {
    override fun cost(): Int {
        return 1000
    }
}

class DarkRoast(): Beverage("DarkRoast") {
    override fun cost(): Int {
        return 2000
    }
}

class HouseBlendSteamMilkAndMocha(): Beverage("HouseBlendSteamMilkAndMocha") {
    override fun cost(): Int {
        return 6000
    }
}

class HouseBlendWithWhipandSoy(): Beverage("HouseBlendWithWhipandSoy") {
    override fun cost(): Int {
        return 5500
    }
}