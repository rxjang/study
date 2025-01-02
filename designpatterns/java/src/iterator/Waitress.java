package iterator;

import java.util.Iterator;

public class Waitress {
    Menu breakfastMenu;
    Menu lunchMenu;
    Menu cafeMenu;

    public Waitress(Menu pancakeHouseMenu, Menu dinerMenu, Menu cafeMenu) {
        this.breakfastMenu = pancakeHouseMenu;
        this.lunchMenu = dinerMenu;
        this.cafeMenu = cafeMenu;
    }

    void printMenu() {
        Iterator pancakeIterator = breakfastMenu.createIterator();
        Iterator dinerIterator = lunchMenu.createIterator();
        Iterator cafeIterator = cafeMenu.createIterator();
        System.out.println("메뉴\n----\n아침 메뉴");
        printMenu(pancakeIterator);
        System.out.println("\n점심 메뉴");
        printMenu(dinerIterator);
        System.out.println("\n저녁 메뉴");
        printMenu(cafeIterator);
    }

    void printMenu(Iterator<MenuItem> iterator) {
       while(iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            System.out.print(menuItem.getName() + " ");
            System.out.println(menuItem.getPrice());
            System.out.println(menuItem.getDescription());
        }
    }


    void printVegetarianMenu(Iterator<MenuItem> iterator) {
        while(iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            if(isItemVegetarian(menuItem.getName(), iterator)) {
                System.out.print(menuItem.getName() + " ");
                System.out.println(menuItem.getPrice());
                System.out.println(menuItem.getDescription());
            }
        }

    }

    boolean isItemVegetarian (String name, Iterator<MenuItem> iterator) {
        while(iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            if(menuItem.getName().equals(name)) {
                return  menuItem.isVegetarian();
            }
        }
        return false;
    }
}
