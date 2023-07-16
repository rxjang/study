package chap03.item10;

public class GoodColorPoint {
    private final Point point;
    private final Color color;

    public GoodColorPoint(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public Point asPoint() {
        return point;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof GoodColorPoint))
            return false;
        GoodColorPoint cp = (GoodColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }
}
