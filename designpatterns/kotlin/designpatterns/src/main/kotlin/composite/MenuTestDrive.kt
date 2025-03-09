package composite


fun main() {
    val pancakeHouseMenu: MenuComponent = Menu("팬케이크 하우스 메뉴", "아침 메뉴", mutableListOf())
    val dinerMenu: MenuComponent = Menu("객체마을 식당 메뉴", "점심 메뉴", mutableListOf())
    val cafeMenu: MenuComponent = Menu("카페 메뉴", "저녁 메뉴", mutableListOf())
    val dessertMenu: MenuComponent = Menu("디저트 메뉴", "디저트를 즐겨보세요!", mutableListOf())

    val allMenus: MenuComponent = Menu("전체 메뉴", "전체 메뉴", mutableListOf(pancakeHouseMenu, dinerMenu, cafeMenu))

    dinerMenu.add(
        MenuItem(
            "파스타",
            "마리나라 소스 스파게티",
            3.89,
            true,
        )
    )
    dinerMenu.add(dessertMenu)

    dessertMenu.add(
        MenuItem(
            "애플 파이",
            "바삭바삭한 크러스트에 바날라 아이스크림이 얹혀 있는 애플 파이",
            1.59,
            true,
        )
    )

    val waitress = Waitress(allMenus)
    waitress.printMenu()
}
