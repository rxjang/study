package chap02.item2;

public class Item2Test {
    public static void main(String[] args) {

        NutritionFacts nf = new NutritionFacts.Builder(1, 1).build();

        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.SAUSAGE)
                .addTopping(Pizza.Topping.ONION)
                .build();

    }
}
