package composite

class Waitress(
    val allMenus: MenuComponent,
) {

    fun printMenu() {
        allMenus.print()
    }
}