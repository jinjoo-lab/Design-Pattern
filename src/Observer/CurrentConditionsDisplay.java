package Observer;

public class CurrentConditionsDisplay implements Observer , DisplayElement{
    private float temperature;
    private float humidity;
    private float pressure;

    private WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData){
        this.weatherData = weatherData;
        weatherData.register(this);
    }

    @Override
    public void update() {
        this.temperature = this.weatherData.getTemperature();
        this.humidity = this.weatherData.getHumidity();
        this.pressure = this.weatherData.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.println("T : "+temperature+" H : "+humidity+" P : "+pressure);
    }
}
