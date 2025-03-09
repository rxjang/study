package composite

class Menu(
    override val name: String,
    override val description: String,
    private val menuComponents: MutableList<MenuComponent>
): MenuComponent(name, description, menuComponents.sumOf { it.price }, false) {

    override fun add(menuComponent: MenuComponent) {
        menuComponents.add(menuComponent)
    }

    override fun remove(menuComponent: MenuComponent) {
        menuComponents.remove(menuComponent)
    }

    override fun getChild(i: Int): MenuComponent {
        return menuComponents[i]
    }

    override fun print() {
        println("$name , $description")
        val iterator = menuComponents.iterator()
        while (iterator.hasNext()) {
            val menuComponent = iterator.next()
            menuComponent.print()
        }
    }
}