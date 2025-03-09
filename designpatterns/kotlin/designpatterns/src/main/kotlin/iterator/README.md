# Iterator Pattern

객체마을에는 식당과 팬케이크 하우스가 있다. 이 둘은 합병되기로 했다. 그런데 식당에서는 메뉴를 ArrayList에, 팬케이크 하우스에는 배열에 저장한다. 
두 식당은 MemuItem을 구현하는 방법에 대해서는 합의를 봤다.
```kotlin
data class MenuItem(
    val name: String,
    val description: String,
    val vegetarian: Boolean,
    val price: Double,
)
```
식당과 펜케이크 하우스 모두 메뉴 항목들을 저장하기 위해 많은 시간을 투자해 코드를 만들었고, 이를 의존하는 코드도 많다.
```kotlin
class PancakeHouseMenu {
    
    val menuItems = mutableListOf<MenuItem>(
        MenuItem("레귤러 펜케이크 세트", "달걀 후라이와 소시지가 겉들여진 팬케이크", false, 3.49),
        MenuItem("블루베리 팬케이크", "신선한 블루베리 시럽으로 만든 펜케이크", true, 3.59),
    )
    
    fun addItem(name: String, description: String, vegetarian: Boolean, price: Double) {
        val menuItem = MenuItem(name, description, vegetarian, price)
        menuItems.add(menuItem)
    }
}

class DinerMenu {

    companion object {
        const val MAX_ITEMS = 6
    }
    var numberOfItems = 0
    val menuItems = arrayOfNulls<MenuItem>(MAX_ITEMS)

    init {
        addItem("채식주의자용 BLT", "통밀 위에 (식물성)베이컨, 상추, 토마토를 얹은 메뉴", true, 2.99)
        addItem("핫도그", "사워크라우트, 갖은 양념, 양파, 치즈가 곁들여진 핫도그", false, 3.05)
    }

    fun addItem(name: String, description: String, vegetarian: Boolean, price: Double) {
        val menuItem = MenuItem(name, description, vegetarian, price)
        if (numberOfItems >= MAX_ITEMS) {
            println("죄송합니다. 메뉴가 꽉 찼습니다. 더 이상 추가할 수 없습니다.")
        } else {
            menuItems[numberOfItems] = menuItem
            numberOfItems += 1
        }
    }
}
```
메뉴 구현 방식이 다르면 무슨 문제가 생길까? 두 메뉴를 사용하는 클라이언트를 만들어보자. 웨이트리스의 자격 요건은 다음과 같다.
* printMenu(): 메뉴에 있는 모든 항목을 출력
* printBreakfastMenu(): 아침 식사 항목만 출력
* printLunchMenu(): 점심 식사 항목만 출력
* printVegetarianMenu(): 채식주의자용 메뉴 항목만 출력
* isItemVegetarian(name): 해당 항목이 채식주의자용인지 확인

웨이트리스 코드를 구현 할 땐, 각 메소드마다 항 상 두 메뉴를 이용해서 각 아이템에 대해서 반복적인 작업을 수행하기 위해 두 개의 순환문을 써야한다. 
또 다른 구현법을 사용하는 레스토랑이 있다면 반복문은 n개로 늘어날 것이다. 
```kotlin
 fun printMenu() {
    val pancakeHouseMenu = PancakeHouseMenu()
    val dinerMenu = DinerMenu()
    
    pancakeHouseMenu.menuItems.forEach { 
        println("${it.name} ${it.price} ${it.description}")
    }

    dinerMenu.menuItems.filterNotNull().forEach {
        println("${it.name} ${it.price} ${it.description}")
    }
}
```
그러면 어떻게 해야 할까? 만약 각 메뉴에 대한 똑같은 인터페이스를 구현하게 된다면 편리할 것이다. 어떻게 하면 인터페이스를 통합할 수 있을까?
여기서는 각 메뉴에서 리턴되는 객체 컬렉의 형식이 다르기 때문에 반복을 작업하는 방법이 달라지는 부분이다. 이 반복을 캡슐화할 방법을 찾아보자.

> **이터레이터 패턴**은 컬렉션 구현 방법을 노출시키지 않으면서도 그 집합체 안에 들어있는 모든 항목에 접근할 수 있게 해주는 방법을 제공해 준다.


이터레이터 패턴에 대해서 가장 먼저 알아야 할 것은 바로 Iterator라는 인터페이스에 의존한다는 점이다.
```kotlin
interface Iterator<T> {
    fun hasNext(): Boolean
    fun next(): T
}
```
이 인터페이스가 있으면 배열, 리스트, 해시테이블은 물론 그 어떤 종류의 객체 컬렉션에 대해서도 반복자를 구할 수 있다.  
이제 DinnerMenu에 이를 적용해보자
```kotlin
class DinerMenuIterator(
    private val items: Array<MenuItem?>
): Iterator<MenuItem> {

    private var position = 0

    override fun hasNext(): Boolean {
        return !(position >= items.size || items[position] == null)
    }

    override fun next(): MenuItem {
        val menuItem = items[position]
        position++
        return menuItem!!
    }
}

class DinerMenu {
    // ...
    fun createIterator(): Iterator<MenuItem> {
        return DinerMenuIterator(menuItems)
    }
}

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
```
Iterator가 장착되었으므로 waitress는 다음과 같은 이점을 누릴 수 있게 되었다.
* 메뉴 구현법이 캡슐화 되었다. waitress 입장에서는 메뉴에서 메뉴 항목의 컬렉션을 어떤 식으로 저장하는지 전혀 알 수 없다.
* iterator만 구현한다면 어떤 컬렉션이든 다형성을 활용하여 한 개의 순환문으로 처리할 수 있다. 
* waitress에서는 인터페이스만 알고 있으면된다.

> **단일 역할 원칙**: 클래스를 바꾸는 이유는 한 가지 뿐이어야 한다.   
클래스를 고치는 것은 최대한 피해야 한다. 코드를 변경하다 온갖 문제가 생길 수 있기 때문이다.

> **응집도**란 한 클래스 또는 모듈이 특정 목적 또는 역할을 얼마나 일관되게 지원하는 지를 나타내는 척도라고 할 수 있다. 
> 어떤 모듈 또는 클래스의 응집도가 높다는 것은 일련의 서로 연관된 기능이 서로 묶여 있다는 것을, 
> 응집도가 낮다는 것은 서로 상관 없는 기능들이 묶여있다는 것을 뜻한다. 



