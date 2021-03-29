package factory.pizza;

public class NYPizzaStore extends PizzaStore{

    @Override
    Pizza createPizza(String item) {
        Pizza pizza = null;

        if(item.equals("cheese")) {
            pizza = new NYStyleCheesePizza();
        } else if(item.equals("pepperoni")) {
            pizza = new NYStylePepperoniPizza();
        } else if(item.equals("clam")) {
            pizza = new NYStyleClamPizza();
        } else if(item.equals("veggie")) {
            pizza = new NYStyleVeggiePizza();
        }
        return pizza;
    }
}
