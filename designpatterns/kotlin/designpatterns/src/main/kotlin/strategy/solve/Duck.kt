package strategy.solve

abstract class Duck(
    private val flyBehavior: FlyBehavior,
    private val quackBehavior: QuackBehavior,
) {
    fun performFly() {
        flyBehavior.fly()
    }

    fun performQuack() {
        quackBehavior.quack()
    }

    abstract fun display()

    fun swim() {
        println("swim")
    }
}

class MallardDuck: Duck(flyBehavior = FlyWithWings(), quackBehavior = Quack()) {
    override fun display() {
        println("MallardDuck")
    }

}

fun main() {
    val duck: Duck = MallardDuck()
    duck.performQuack()
    duck.performFly()
}