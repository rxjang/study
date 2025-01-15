package decorator.solve

fun main() {
    val beverage: Beverage = DarkRoast()
    println(" ${beverage.description}: ${beverage.cost()} ₩")
    var beverage2: Beverage = DarkRoast()
    beverage2 = Soy(beverage2)
    beverage2 = Whip(beverage2)
    beverage2 = Whip(beverage2)
    println(" ${beverage2.description}: ${beverage2.cost()} ₩")
}