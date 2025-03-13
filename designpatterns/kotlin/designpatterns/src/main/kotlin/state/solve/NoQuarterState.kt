package state.solve

class NoQuarterState(
    val gumballMachine: GumballMachine,
): State {
    override fun insertQuarter() {
        println("동전을 넣으셨습니다.")
        gumballMachine.changeState(gumballMachine.hasQuarterState)
    }

    override fun ejectQuarter() {
        println("동전을 넣어주세요")
    }

    override fun turnCrank() {
        println("동전을 넣어주세요.")
    }

    override fun dispense() {
        println("동전을 넣어주세요.")
    }
}