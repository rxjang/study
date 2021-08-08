package chap07;

public class PointTest {
    public static void main(String[] args) {
        Points3D p3 = new Points3D();
        System.out.println(p3.getLocation());
    }
}

class Points {
    int x = 10;
    int y = 20;

    Points(int x, int y) {
        this.x = x;
        this.y = y;
    }

    String getLocation() {
        return "x :" + x + " , y :" + y;
    }
}

class Points3D extends Points {
    int z = 30;

    public Points3D() {
        this(100, 200, 300);
    }

    Points3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    String getLocation() {
        return "x :" + x + " , y :" + y + " , z :" + z;
    }
}


