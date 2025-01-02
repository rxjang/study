package decorator.starbuzz;

public abstract class Beverage {

    String description = "제목 없음";

    public enum Size { TALL, GRANDE, VENTI };

    Size size = Size.TALL;

    public String getDescription() {
        return description;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public abstract double cost();
}
