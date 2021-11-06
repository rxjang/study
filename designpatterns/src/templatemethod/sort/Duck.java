package templatemethod.sort;

public class Duck implements Comparable{

    String name;

    int weight;

    public Duck(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + ", weight:" + weight;
    }

    @Override
    public int compareTo(Object o) {
        Duck otherDuck = (Duck) o;

        return Integer.compare(this.weight, otherDuck.weight);
    }
}
