package Decorator;

public class HouseBlend implements Beverage{
    @Override
    public String getDescription() {
        return "House Blend :";
    }

    @Override
    public double cost() {
        return 3.0f;
    }
}
