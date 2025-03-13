package state.solve

class SoldState(
    private val gumballMachine: GumballMachine
): State {
    override fun insertQuarter() {
        println("잠깐만 기다려 주세요. 알맹이가 나가고 있습니다. ")
    }

    override fun ejectQuarter() {
        println("이미 알맹이를 뽑으셨습니다.")
    }

    override fun turnCrank() {
        println("손잡이는 한 번만 돌려주세요.")
    }

    override fun dispense() {
        gumballMachine.releaseBall()
        if (gumballMachine.count > 0) {
            gumballMachine.changeState(gumballMachine.noQuarterState)
        } else {
            println("Oops, out of gumballs!")
            gumballMachine.changeState(gumballMachine.soldOutState)
        }
    }
}