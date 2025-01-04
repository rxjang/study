package strategy.problem1

abstract class Duck {

    open fun quack() {
        println("quack")
    }

    fun swim() {
        println("swim")
    }

    open fun fly() {
        println("fly")
    }

    abstract fun display()
}

class MallardDuck: Duck() {
    override fun display() {
        println("MallardDuck")
    }
}

class RedheadDuck: Duck() {
    override fun display() {
        println("RedheadDuck")
    }
}

class RubberDuck: Duck() {

    override fun quack() {
        println("삑삑")
    }

    override fun display() {
        println("RubberDuck")
    }

    override fun fly() {}

}

class DecoyDuck: Duck() {

    override fun quack() {}

    override fun display() {
        println("DecoyDuck")
    }

    override fun fly() {}

}