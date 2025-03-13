package state.problem

fun main() {
    val gumballMachine = GumballMachine(5)

    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()

    gumballMachine.insertQuarter()
    gumballMachine.ejectQuarter()
    gumballMachine.turnCrank()

    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
    gumballMachine.ejectQuarter()


    gumballMachine.insertQuarter()
    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
    gumballMachine.insertQuarter()
    gumballMachine.turnCrank()
}