package iterator

class DinerMenuIterator(
    private val items: Array<MenuItem?>
): Iterator<MenuItem>{

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