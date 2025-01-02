package decorator.starbuzz;

public class DarkRoast extends Beverage {

    public DarkRoast() {
        description = "최고의 다크로스트 커피";
    }

    @Override
    public double cost() {
        return .99;
    }
}
