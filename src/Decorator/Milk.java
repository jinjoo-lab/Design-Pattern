package Decorator;

public class Milk extends CondimentDecorator{

    public Milk(Beverage beverage){
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return this.beverage.getDescription()+" + MILK ";
    }

    @Override
    public double cost() {
        return this.beverage.cost()+1.0f;
    }
}
