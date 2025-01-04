package strategy.solve

interface Animal {
    fun makeSound()
}

class Dog: Animal {
    override fun makeSound() {
        bark()
    }

    fun bark() {
        println("bark")
    }
}

class Cat: Animal {
    override fun makeSound() {
        meow()
    }

    fun meow() {
        println("meow")
    }

}

fun main() {
    // 변수 d를 Dog로 선언하면 어떤 국체적인 구현에 맞춰서 코딩을 해야한다.
    val d: Dog = Dog()
    d.bark()

    // Dog라는 걸 알고 있긴 하지만 다향성을 활용해 Animal에 대한 래퍼런스를 써도 된다.
    val animal: Animal = Dog()
    animal.makeSound()

    // 구체적으로 구현된 객체를 실행시에 대입하는 것이 더 바람직하다.
    val a: Animal = getAnimal()
    a.makeSound()
}


fun getAnimal(): Animal {
    TODO()
}