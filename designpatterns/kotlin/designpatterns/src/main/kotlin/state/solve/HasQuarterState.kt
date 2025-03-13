package state.solve

import kotlin.random.Random

class HasQuarterState(
    val gumballMachine: GumballMachine
): State {

    private val randomWinner = Random(System.currentTimeMillis())

    override fun insertQuarter() {
        println("동전은 한 개만 넣어 주세요.")
    }

    override fun ejectQuarter() {
        println("동전이 반환됩니다.")
        gumballMachine.changeState(gumballMachine.noQuarterState)
    }

    override fun turnCrank() {
        println("손잡이를 돌리셨습니다.")
        val winner = randomWinner.nextInt(10)
        if (winner == 0 && gumballMachine.count > 1) {
            gumballMachine.changeState(gumballMachine.winnerState)
        } else {
            gumballMachine.changeState(gumballMachine.soldState)
        }
    }

    override fun dispense() {
        println("알맹이가 나갈 수 없습니다.")
    }
}