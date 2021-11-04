package templatemethod.barista;

public class BeverageTestDrive {
    public static void main(String[] args) {
        Tea tea = new Tea();

        tea.prepareRecipe();

        CoffeeWithHook coffeeWithHook = new CoffeeWithHook();
        System.out.println("\n커피 준비중...");
        coffeeWithHook.prepareRecipe();
    }
}
