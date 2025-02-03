package org.example;

public class SquareNumber implements Runnable{

    private final int number;

    private SquareNumber(int number) {
        this.number = number;
    }

    public static SquareNumber create(int number) {
        return new SquareNumber(number);
    }

    @Override
    public void run() {
        int numbers = number * number;
        System.out.println("Square of " + number + " = " + (numbers));
    }

    public int getNumber() {
        return number;
    }
}
