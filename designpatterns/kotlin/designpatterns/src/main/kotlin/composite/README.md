# Composite Pattern

앞서 배웠던 iterator 패턴의 예제에서, 디저트 서브메뉴를 추가 해달라는 요청사항을 받았다.
메뉴 안에 메뉴가 들어가야 하는 상황인 것이다. 새로운 디자인이 필요하다. 새로운 디자인에는 다음과 같은 것이 필요하다.
* 메뉴, 서브 메뉴, 메뉴 항목 등을 모두 집어 넣을 수 있는 트리 형태의 구조가 필요하다. 
* 각 메뉴에 있는 모든 항목에 대해 돌아가면서 어떤 작업을 할 수 있는 방법을 제공해야 한다.
* 더 유연한 방법으로 아이템에 대해 반복작업을 수행할 수 있어야 한다. 

이 문제를 컴포지트 패턴을 통해 해결해 보자.
> **컴포지트 패턴**을 이용하면 객체들을 트리 구조로 구성하여 부분과 전체를 나타내는 계층구조로 만들 수 있다. 
> 이 패턴을 이용하면 클라이언트에게 개별 객체와 다른 객체들로 구성된 복합 객체를 똑같은 방법으로 다룰 수 있다. 

```kotlin
abstract class MenuComponent(
    open val name: String,
    open val description: String,
    open val price: Double,
    open val vegetarian: Boolean,
) {
    open fun add(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }

    open fun remove(menuComponent: MenuComponent) {
        throw UnsupportedOperationException()
    }

    open fun getChild(i: Int): MenuComponent {
        throw UnsupportedOperationException()
    }

    open fun print() {
        throw UnsupportedOperationException()
    }
}

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
}

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

class Waitress(
    val allMenus: MenuComponent,
) {

    fun printMenu() {
        allMenus.print()
    }
}

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
```
앞전에 한 클래스에한 역할만 맡아야 한다고 했다. 이 패턴에서는 한 클래스에 두 가지 역할을 집어넣고 있다. 
* 계층구조를 관리하는 일
* 메뉴하고 관련된 작업을 처리하는 일

컴포지트 패턴에서는 단일 역할 원칙을 깨는 대신 투명성을 확보한다. 여기서 투명성이란, Component 인터페이스에 자식들을 관리하기 위한 기능과 leaf로써의 기능을 전부 집어 넣음으로써 
클라이언트에서 복합 객체와 leaf노드를 똑같은 방식으로 처리할 수 있도록 하는 것이다. 
어떤 원소가 복합 객체인지 leaf 노드인지가 클라이언트 입장에서는 투명하게 느껴지는 것이다.  
즉, 이는 상황에 따라 원칙을 적절하게 사용해야 한다는 것을 보여주는 대표적인 사례인 것이다. 