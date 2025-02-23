package adapter

fun main() {
    val duck = MallardDuck()
    val turkey = WildTurkey()
    val turkeyAdapter: Duck = TurkeyAdapter(turkey)

    println("The Turkey says..")
    turkey.gobble()
    turkey.fly()

    println("The Duck says..")
    testDuck(duck)

    println("The TurkeyAdapter says....")
    testDuck(turkeyAdapter)
}

fun testDuck(duck: Duck) {
    duck.quack()
    duck.fly()
}
