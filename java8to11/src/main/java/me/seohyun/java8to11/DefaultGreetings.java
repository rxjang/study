package me.seohyun.java8to11;

public class DefaultGreetings implements Greetings{

    String name;

    public DefaultGreetings(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
