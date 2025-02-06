package singleton.solution

class DCLSingleton private constructor() {

    companion object {
        @Volatile
        private var uniqueInstance: DCLSingleton? = null

        fun getInstance(): DCLSingleton {
            return synchronized(this) {
                uniqueInstance ?: DCLSingleton()
            }
        }
    }
}