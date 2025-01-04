package strategy.problem2

class MallardDuck: Duck(), Flyable, Quackable {

    override fun display() {
        println("MallardDuck")
    }

    override fun fly() {
        println("fly")
    }

    override fun quack() {
        println("quack")
    }
}

class RedheadDuck: Duck(), Flyable, Quackable {
    override fun display() {
        println("RedheadDuck")
    }

    override fun fly() {
        println("fly")
    }

    override fun quack() {
        println("quack")
    }
}

class RubberDuck: Duck(), Quackable {

    override fun display() {
        println("RubberDuck")
    }

    override fun quack() {
        println("quack")
    }

}

class DecoyDuck: Duck() {

    override fun display() {
        println("DecoyDuck")
    }

}