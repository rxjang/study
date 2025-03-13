package state.solve

class WinnerState(
    val gumballMachine: GumballMachine,
): State {
    override fun insertQuarter() {
        throw UnsupportedOperationException()
    }

    override fun ejectQuarter() {
        throw UnsupportedOperationException()
    }

    override fun turnCrank() {
        throw UnsupportedOperationException()
    }

    override fun dispense() {
        println("축하드립니다! 알맹이를 하나 더 받으실 수 있습니다.")
        gumballMachine.releaseBall()
        if (gumballMachine.count == 0) {
            gumballMachine.changeState(gumballMachine.soldOutState)
        } else {
            println("더 이상 알맹이가 없습니다.")
            gumballMachine.changeState(gumballMachine.soldOutState)
        }
    }
}