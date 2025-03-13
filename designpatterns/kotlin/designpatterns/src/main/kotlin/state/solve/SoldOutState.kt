package state.solve

class SoldOutState(
    val gumballMachine: GumballMachine
): State{
    override fun insertQuarter() {
        println("매진되었습니다. 다음 기회에 이용해주세요.")

    }

    override fun ejectQuarter() {
        println("매진되었습니다. 다음 기회에 이용해주세요.")
    }

    override fun turnCrank() {
        println("매진되었습니다. 다음 기회에 이용해주세요.")
    }

    override fun dispense() {
        println("알맹이가 나갈 수 없습니다..")
    }
}