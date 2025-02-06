package singleton.problem

class ChocolateBoiler private constructor(
    private var empty: Boolean = true,
    private var boiled: Boolean = false,
) {

    companion object {
        private var uniqueInstance: ChocolateBoiler? = null

        fun getInstance(): ChocolateBoiler {
            return uniqueInstance ?: ChocolateBoiler()
        }
    }

    fun fill() {
        if (isEmpty()) {
            empty = false
            boiled = false
        }
    }

    fun drain() {
        if (!isEmpty() && isEmpty()) {
            empty = true
        }
    }

    fun boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true
        }
    }

    fun isEmpty(): Boolean {
        return empty
    }

    fun isBoiled(): Boolean {
        return boiled
    }
}