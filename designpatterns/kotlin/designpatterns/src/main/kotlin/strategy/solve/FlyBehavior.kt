package strategy.solve

interface FlyBehavior {
    fun fly()
}

class FlyWithWings: FlyBehavior {

    override fun fly() {
        println("Fly With Wings")
    }

}

class FlyNoWay(): FlyBehavior {

    override fun fly() {
        // Do noting
    }

}