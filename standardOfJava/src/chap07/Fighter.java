package chap07;

public class Fighter extends Unit implements Fightable {
    public void move( int x, int y) {

    }

    public void attack(Unit u) {

    }
}

interface Fightable extends Movable, Attackable {}

interface Movable { void move(int x, int y);}
interface Attackable { void attack(Unit u);}
