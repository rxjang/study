package templatemethod.problem

class Coffee {

    fun prepareRecipe() {
        boilWater()
        brewCoffeeGrinds()
        pourInCup()
        addSugarAndMilk()
    }

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun brewCoffeeGrinds() {
        println("필터를 통해서 커피를 우려내는 중")
    }

    fun pourInCup() {
        println("컵에 따르는 중")
    }

    fun addSugarAndMilk() {
        println("설탕과 우유를 추가하는 중")
    }
}