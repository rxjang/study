package chap12;

import java.util.Collections;
import java.util.Comparator;

public class FruitBoxEx4 {
    public static void main(String[] args) {
        VegetableBox<Tomato> tomatoBox = new VegetableBox<>();
        VegetableBox<Onion> onionBox = new VegetableBox<>();

        tomatoBox.add(new Tomato("RedTomato", 100));
        tomatoBox.add(new Tomato("RedTomato", 150));
        tomatoBox.add(new Tomato("RedTomato", 200));

        onionBox.add(new Onion("WhiteOnion", 300));
        onionBox.add(new Onion("WhiteOion", 200));
        onionBox.add(new Onion("WhiteOion", 100));

        Collections.sort(tomatoBox.getList(), new TomatoComp());
        Collections.sort(onionBox.getList(), new OnionComp());
        System.out.println(tomatoBox);
        System.out.println(onionBox);
        System.out.println();
        Collections.sort(tomatoBox.getList(), new VegetableComp());
        Collections.sort(onionBox.getList(), new VegetableComp());
        System.out.println(tomatoBox);
        System.out.println(onionBox);
        System.out.println();
    }

}

class Vegetable {
    String name;
    int weight;

    public Vegetable(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String toString() { return name + "(" + weight + ")"; }
}

class Tomato extends Vegetable {
    public Tomato(String name, int weight) {
        super(name, weight);
    }
}

class Onion extends Vegetable {
    public Onion(String name, int weight) {
        super(name, weight);
    }
}

class TomatoComp implements Comparator<Tomato> {
    public int compare(Tomato t1, Tomato t2) {
        return t2.weight - t1.weight;
    }
}

class OnionComp implements Comparator<Onion> {
    public int compare(Onion t1, Onion t2) {
        return t2.weight - t1.weight;
    }
}

class VegetableComp implements Comparator<Vegetable> {
    public int compare(Vegetable t1, Vegetable t2) {
        return t1.weight - t2.weight;
    }
}

class VegetableBox<T extends Vegetable> extends Box<T>{}

