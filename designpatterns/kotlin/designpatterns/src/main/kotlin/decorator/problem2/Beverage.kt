package decorator.problem2

abstract class Beverage(
    val description: String,
    open val milk: Int?,
    open val soy: Int?,
    open val mocha: Int?,
    open val whip: Int?,
) {

    fun hasMilk(): Boolean = milk != null
    fun hasSoy(): Boolean = soy != null
    fun hasMocha(): Boolean = mocha != null
    fun hasWhip(): Boolean = whip != null

    open fun cost(): Int {
        var cost = 0
        if (hasMilk()) cost += 1_000
        if (hasSoy()) cost += 500
        if (hasMocha()) cost += 500
        if (hasWhip()) cost += 500
        return cost
    }
}

class DarkRoast(
    override val milk: Int?,
    override val soy: Int?,
    override val mocha: Int?,
    override val whip: Int?,
): Beverage("DarkRoast", milk, soy, mocha, whip) {

    override fun cost(): Int {
        return 2_000 + super.cost()
    }

}