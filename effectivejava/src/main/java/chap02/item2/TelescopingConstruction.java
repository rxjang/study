package chap02.item2;

public class TelescopingConstruction {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrates;

    public TelescopingConstruction(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public TelescopingConstruction(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public TelescopingConstruction(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public TelescopingConstruction(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public TelescopingConstruction(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrates) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrates = carbohydrates;
    }
}
