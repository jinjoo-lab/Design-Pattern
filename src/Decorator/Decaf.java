package Decorator;

public class Decaf implements Beverage{
    @Override
    public String getDescription() {
        return "DECAF : ";
    }

    @Override
    public double cost() {
        return 5.0f;
    }
}
