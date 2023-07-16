package chap03.item10;

import java.util.Objects;

public class ColorPoint extends Point{
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;

        // o가 일반 Point면 생상을 무시하고 비교한다.
        if (!(o instanceof ColorPoint)) return o.equals(this);

        // o가 ColorPoint면 색상까지 비교한다.
        return (super.equals(o)) && ((ColorPoint) o).color == color;
    }

}
