package Decorator;

public class Soy extends CondimentDecorator{

    public Soy(Beverage beverage){
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return this.beverage.getDescription()+" + 두유 ";
    }

    @Override
    public double cost() {
        return this.beverage.cost()+2.0f;
    }
}
