package adapter

interface Duck {
    fun quack()
    fun fly()
}

class MallardDuck: Duck {
    override fun quack() {
        println("quack")
    }

    override fun fly() {
        println("fly")
    }

}