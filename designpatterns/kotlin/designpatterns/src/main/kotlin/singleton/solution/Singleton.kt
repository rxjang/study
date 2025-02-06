package singleton.solution

class Singleton private constructor() {

    companion object {
        private var uniqueInstance: Singleton? = null

        fun getInstance(): Singleton {
            return synchronized(this) {
                uniqueInstance ?: Singleton()
            }
        }
    }
}