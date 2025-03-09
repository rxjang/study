package composite

class MenuItem(
    override val name: String,
    override val description: String,
    override val price: Double,
    override val vegetarian: Boolean,
): MenuComponent(name, description, price, vegetarian) {

    override fun print() {
        val isVegetarian = if (vegetarian) "(v)" else ""
        println("$name $isVegetarian , $price")
    }
}