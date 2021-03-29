package factory.pizza;

public class CaliforniaPizzaStore extends PizzaStore{

    @Override
    Pizza createPizza(String item) {
        Pizza pizza = null;

        if(item.equals("cheese")) {
            pizza = new CaliforniaStyleCheesePizza();
        } else if(item.equals("pepperoni")) {
            pizza = new CaliforniaStylePepperoniPizza();
        } else if(item.equals("clam")) {
            pizza = new CaliforniaStyleClamPizza();
        } else if(item.equals("veggie")) {
            pizza = new CaliforniaStyleVeggiePizza();
        }
        return pizza;
    }
}
