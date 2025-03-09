package iterator

class Waitress(
    private val pancakeHouseMenu: PancakeHouseMenu = PancakeHouseMenu(),
    private val dinerMenu: DinerMenu  = DinerMenu(),
) {

    fun printMenu() {
        val pancakeIterator = pancakeHouseMenu.creatIterator()
        val dinerIterator = dinerMenu.createIterator()

        println("아침 메뉴")
        printMenu(pancakeIterator)
        println("점심 메뉴")
        printMenu(dinerIterator)
    }

    private fun printMenu(iterator: Iterator<MenuItem>) {
        while (iterator.hasNext()) {
            val menuItem = iterator.next()
            println("${menuItem.name} ${menuItem.price} ${menuItem.description}")
        }
    }
}