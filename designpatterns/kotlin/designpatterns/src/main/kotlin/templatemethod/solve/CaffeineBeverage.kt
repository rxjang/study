package templatemethod.solve

abstract class CaffeineBeverage {

    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCoup()
        addCondiments()
    }

    abstract fun brew()
    abstract fun addCondiments()

    fun boilWater() {
        println("물 끓이는 중")
    }

    fun pourInCoup() {
        println("컵에 따르는 중")
    }
}

class Tea(): CaffeineBeverage() {
    override fun brew() {
        println("차를 우려내는 중")
    }

    override fun addCondiments() {
        println("레몬을 추가하는 중")
    }
}

class Coffee(): CaffeineBeverage() {
    override fun brew() {
        println("필터로 커피를 우려내는 중")
    }

    override fun addCondiments() {
        println("설탕과 커피를 추가하는 중")
    }
}