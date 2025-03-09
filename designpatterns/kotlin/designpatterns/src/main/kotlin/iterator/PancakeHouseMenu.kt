package iterator

class PancakeHouseMenu {

    val menuItems = mutableListOf<MenuItem>(
        MenuItem("레귤러 펜케이크 세트", "달걀 후라이와 소시지가 겉들여진 팬케이크", false, 3.49),
        MenuItem("블루베리 팬케이크", "신선한 블루베리 시럽으로 만든 펜케이크", true, 3.59),
    )

    fun addItem(name: String, description: String, vegetarian: Boolean, price: Double) {
        val menuItem = MenuItem(name, description, vegetarian, price)
        menuItems.add(menuItem)
    }

    fun creatIterator(): Iterator<MenuItem> {
        return PancakeHouseMenuIterator(menuItems)
    }
}