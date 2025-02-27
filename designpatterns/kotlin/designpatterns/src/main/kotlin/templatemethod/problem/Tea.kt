package templatemethod.problem

class Tea {

    fun prepareRecipe() {
        boilWater()
        steepTeaBag()
        addLemon()
        pourInCup()
    }

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun steepTeaBag() {
        println("차를 우려내는 중")
    }

    fun addLemon() {
        println("레몬을 추가하는 중")
    }

    fun pourInCup() {
        println("컵에 따르는 중")
    }
}