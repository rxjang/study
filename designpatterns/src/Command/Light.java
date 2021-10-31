package Command;

public class Light {
    String place;

    public Light() { }

    public Light(String place) {
        this.place = place;
    }

    public void on() {
        if(place != null) {
            System.out.println(place + " light is on");
        } else {
            System.out.println("Light is on");
        }
    }

    public void off() {
        if(place != null) {
            System.out.println(place + " light is off");
        } else {
            System.out.println("Light is off");
        }
    }
}
