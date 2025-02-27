package templatemethod.hook

abstract class CaffeineBeverageWithHook {

    fun prepareRecipe() {
        boilWater()
        brew()
        pourInCoup()
        if (customerWantsCondiments()) {
            addCondiments()
        }
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

    fun customerWantsCondiments(): Boolean {
        return true
    }
}