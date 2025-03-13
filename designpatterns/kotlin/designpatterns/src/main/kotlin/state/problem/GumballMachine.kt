package state.problem

class GumballMachine(
    count: Int,
) {
    companion object {
        val SOLD_OUT = 0
        val NO_QUARTER = 1
        val HAS_QUATER = 2
        val SOLD = 3
    }

    private var count: Int = count
    private var state = SOLD_OUT

    init {
        if (count > 0) {
            state = NO_QUARTER
        }
    }

    // 동전 투입
    fun insertQuarter() {
        when (state) {
            HAS_QUATER -> println("동전은 한 개만 넣어 주세요.")
            NO_QUARTER -> {
                state = HAS_QUATER
                println("동전을 넣으셨습니다.")
            }
            SOLD_OUT -> {
                println("매진되었습니다. 다음 기회에 이용해주세요.")
            }
            SOLD -> {
                println("잠깐만 기다려 주세요. 알맹이가 나가고 있습니다. ")
            }
        }
    }

    // 동전 반환
    fun ejectQuarter() {
        when (state) {
            HAS_QUATER -> {
                println("동전이 반환됩니다.")
                state = NO_QUARTER
            }
            NO_QUARTER -> {
                println("동전을 넣어주세요")
            }
            SOLD_OUT -> {
                println("동전을 넣지 않으셨습니다. 동전이 반환되지 않습니다. ")
            }
            SOLD -> {
                println("이미 알맹이를 뽑으셨습니다.")
            }
        }
    }

    // 손잡이 돌리기
    fun turnCrank() {
        when (state) {
            HAS_QUATER -> {
                println("손잡이를 돌리셨습니다.")
                state = SOLD
                dispense()
            }
            NO_QUARTER -> {
                println("동전을 넣어주세요.")
            }
            SOLD_OUT -> {
                println("매진되었습니다.")
            }
            SOLD -> {
                println("손잡이는 한 번만 돌려주세요.")
            }
        }
    }

    // 알맹이 꺼내기
    fun dispense() {
        when (state) {
            HAS_QUATER -> {
                println("알맹이가 나갈 수 없습니다.")
            }
            NO_QUARTER -> {
                println("동전을 넣어주세요.")
            }
            SOLD_OUT -> {
                println("매진입니다.")
            }
            SOLD -> {
                println("알맹이가 나가고 있습니다.")
                count -= 1
                state = if (count == 0) {
                    println("더 이상 알맹이가 없습니다.")
                    SOLD_OUT
                } else {
                    NO_QUARTER
                }
            }
        }
    }

}