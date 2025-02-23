package adapter

interface Turkey {
    fun gobble()
    fun fly()
}

class WildTurkey: Turkey {
    override fun gobble() {
        println("gobble gobble")
    }

    override fun fly() {
        println("I'm flying a short distance")
    }

}