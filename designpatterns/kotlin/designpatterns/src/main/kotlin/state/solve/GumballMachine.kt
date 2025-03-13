package state.solve

class GumballMachine(
    numberGumballs: Int,
) {
    val soldOutState = SoldOutState(this)
    val noQuarterState = NoQuarterState(this)
    val hasQuarterState = HasQuarterState(this)
    val soldState = SoldState(this)
    val winnerState = WinnerState(this)

    private var state: State = soldOutState
    var count = numberGumballs
        protected set

    init {
        if (numberGumballs > 0) {
            this.state = noQuarterState
        }
    }

    fun insertQuarter() {
        state.insertQuarter()
    }

    fun ejectQuarter() {
        state.ejectQuarter()
    }

    fun turnCrank() {
        state.turnCrank()
        state.dispense()
    }

    fun dispense() {
        state.dispense()
    }

    fun changeState(state: State) {
        this.state = state
    }

    fun releaseBall() {
        println("A gumball comes rolling out this slot...")
        if (count != 0) {
            count -= 1
        }
    }
}
