package chap07;

public class RepairableTest {
    public static void main(String[] args) {
        Tank tank = new Tank();
        Dropship dropship = new Dropship();

        Marine marine = new Marine();
        SCV scv = new SCV();
        scv.repair(tank);
        scv.repair(dropship);
//        scv.repair(marine);
    }
}

interface Repairable {}

class Unit2 {
    int hitPoint;
    final int MAX_HP;
    Unit2(int hp) {
        MAX_HP = hp;
    }
}

class GroupUnit extends Unit2 {
    public GroupUnit(int hp) {
        super(hp);
    }
}

class AirUnit extends Unit2 {
    public AirUnit(int hp) {
        super(hp);
    }
}

class Tank extends GroupUnit implements Repairable {
    public Tank() {
        super(150);
        hitPoint = MAX_HP;
    }

    public String toString() {
        return "Tank";
    }
}

class Dropship extends AirUnit implements Repairable {
    public Dropship() {
        super(125);
        hitPoint = MAX_HP;
    }

    public String toString() {
        return "Dropship";
    }
}

class Marine extends GroupUnit {
    public Marine() {
        super(40);
        hitPoint = MAX_HP;
    }
}

class SCV extends GroupUnit implements Repairable {
    public SCV() {
        super(60);
        hitPoint = MAX_HP;
    }

    void repair(Repairable r) {
        if (r instanceof Unit2) {
            Unit2 u = (Unit2) r;
            while(u.hitPoint!=u.MAX_HP) {
                u.hitPoint ++;
            }
            System.out.println(u.toString() + "의 수리가 끝났습니다. ");
        }
    }
}
