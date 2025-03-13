package state.solve

fun main() {
    val gumballMachine = GumballMachine(5)
    println(gumballMachine)

    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()

    println()

    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()

    println()

}