# 아이템2. 생성자에 매개변수가 많다면 빌더를 고려하라.
## 점층적 생성자 패턴
``` java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbihydrates;

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    ...

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbihydrates) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbihydrates = carbihydrates;
    }
}
```
* 인스턴스를 만들려면 원하는 매갸변수를 모두 포함한 생성자 중 가장 짦은 것을 골라 호출
* 매개변수가 많아지면 클라이언트 코드를 작성하거나 읽기 어려워짐

## 자바빈즈 패턴
매개변수가 없는 생성자로 객체를 만든 후 세터 매서드를 호출해 원하는 매개변수의 값을 설정하는 방식
```java
public class NutritionFacts {
    private int servingSize;
    private int servings;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrates;

    public NutritionFacts() {
    }
    
    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }
    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
```
* 점층적 생성자 패턴의 단점들이 사라짐
* but, 객체 하나를 만들려면 메서드 여러개를 호출해야함
* 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 됨 -> 불변 아이템으로 만들 수 없음

## 빌터 패턴
위의 문제점을 모두 해결해주고 일관성과 가독성이 뛰어난 패턴
```java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrates;

    public static class Builder {
        // 필수 매개변수
        private final int servingSize;
        private final int servings;

        // 선택 매개변수 - 기본값으로 초기화
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;
        
        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }
        
        public Builder calories(int val) {
            calories = val;
            return this;
        }
        
        public Builder fat(int val) {
            fat = val;
            return this;
        }
        
        public Builder sodium(int val) {
            sodium = val;
            return this;
        }
        
        public Builder carbohydrates(int val) {
            carbohydrate = val;
            return this;
        }
        
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }
    
    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrates = builder.carbohydrate;
    }
    
}
```
* 불변 클래스가 되었으며, 모든 매개변수의 기본값들을 한곳에 모아둠
* 빌더의 세터 패턴 메서드들은 자신을 반환하기 때문에 연쇄적으로 호출 할 수 있음

빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다. 
```java
public abstract class Pizza {
    public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
    final Set<Topping> toppings;
    
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        
        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }
        
        abstract Pizza build();
        
        // 하위 클래스는 이 메서드를 재정의해 "this"를 반환해야함
        protected abstract T self();
    }
    
    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}
```
```java
public class NyPizza extends Pizza{
    public enum Size {SMALL, MEDIUM, LARGE}
    private final Size size;
    
    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;
        
        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        // 공변 변환 타이핑
        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
    
    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
```
* 공변 변환 타이핑이 가능하다.
  * 하위 클래스의 메서드가 상위 클래스의 메서드가 정의한 반환 타입이 아닌, 그 하위 타입을 반환하는 기능
* 가변인수 매개변수를 여러 개 사용할 수 있음
* 여러 객체를 순환하면서 만들 수 있음
* 빌더에 넘기는 매개변수에 따라 다른 객체를 만들 수 있음