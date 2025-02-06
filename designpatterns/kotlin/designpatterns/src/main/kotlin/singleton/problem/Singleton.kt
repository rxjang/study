package singleton.problem

class Singleton private constructor() {

    companion object {
        private var uniqueInstance: Singleton? = null

        fun getInstance(): Singleton {
            return uniqueInstance ?: Singleton()
        }
    }
}