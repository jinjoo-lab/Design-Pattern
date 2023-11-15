# 옵저버 패턴

### 구현 상황

![Untitled](https://user-images.githubusercontent.com/84346055/283157893-6faec209-585b-45d2-8202-d1fdaff2c0e5.png)

- WeatherData
    - 온도  , 습도 , 기압
    - 각 정보를 최신화하여 디스플레이에 전달 !
- Display 종류
    - 현재 조건
    - 기상 통계
    - 기상 예보

### 일단 박죠 ~!

**WeatherData**

```java
public class WeatherData {
    private float temperature;
    private float humidity;
    private float pressure;

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void measurementsChanged(){
        // 최신 정보로 온도 , 습도 , 압력 Update !

        // 첫번째 Display :  정보 전달 !
        // 두번째 Display :  정보 전달 !
        // 세번째 Display :  정보 전달 !
    }
}
```

### 문제 상황

1. Display의 종류가 많아진다면? (확장성의 문제)
    - 늘어날때마다 measurementsChange() 메소드에 디스플레이관련 코드를 추가해야 한다.
    - 단순히 **정보를 측정하여 정보를 전달하는 과정**을 반복해야 한다.
        - 어떤 종류의 디스플레이건 똑같은 작업인데…..

## 옵저버 패턴

> **주체**와 **관찰자** 두개의 개념이 등장한다.
>
- 주체 : 데이터를 직접 다루고 상태를 관리하는 객체
- 관찰자(옵저버) : 주체로부터 데이터를 전달받는 객체

> 옵저버 패턴이란 관찰자들이 주체에 등록되어 주체의 상태 변화 시 주체가 자신에게 등록된 관찰자들에게 정보를 전달하는 디자인 패턴
>

### UML

![Untitled](https://user-images.githubusercontent.com/84346055/283157921-6181785c-5b3c-4e90-8e3c-823c42422018.png)

- Subject (주체)
    - attach(o) : 관찰자를 등록
    - detach(o) : 해당 관찰자를 제거
    - notify() : 자신에게 등록된 관찰자들에게 상태 전달
- Observer (관찰자)
    - update() : 주체로부터 정보를 전달받음

### 특징

1. One-To-Many 의존성이 정의된다.
    - 관찰자들은 단 하나의 주체에 의존하게 된다.

### 장점

- 실시간으로 객체의 상태를 다른 객체들에게 전파할 수 있다.
- **느슨한 결합성**
    - 객체 사이의 상호 의존성 최소화 → 유연성 확대

### 단점

- 알림(notify)의 순서를 제어할 수 없다.
    - 결국 인터페이스에 의한 함수 호출이 이루어지기 때문 !
- 옵저버 객체를 등록하고 해지하지 않는다면 메모리 낭비

## Observer 패턴으로 → 문제 해결!

### 구현 범위 정리

```
Observer
	Interface
		1. Observer
		2. Subject
		3. DisplayElement
	Class
		1. WeatherData
		2. CurrentConditionsDisplay : 현재 측정값
		3. StatisticsDisplay : 평균 값 (X)
		4. ForecastDisplay : 기상 예보 (X)
```

### Interface

**Observer**

```java
public interface Observer {
    public void update(float temperature,float humidity,float pressure);
}
```

**Subject**

```java
public interface Subject {
    public void register(Observer observer);
    public void remove(Observer observer);
    public void notify(Observer observer);
}
```

**DisplayElement**

```java
public interface DisplayElement {
    public void display();
}
```

### Class

**WeatherData**

```java
import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject{
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    public WeatherData(){
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public void register(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update(temperature,humidity,pressure);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }
    
    public void setMeasurements(float temperature,float humidity,float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
    
}
```

**CurrentConditionsDisplay**

- Observer 등록의 과정은 어떻게 수행해야 할까?
    - Observer 객체가 생성될 때 등록되도록 한다!
    - 즉 Subject의 구현 객체를 내부 속성으로 가지도록한다.

```java
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
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    @Override
    public void display() {
        System.out.println("T : "+temperature+" H : "+humidity+" P : "+pressure);
    }
}
```

### 추가

- Push : 주체 → 관찰자
- Pull : 관찰자 ← 주체
- 장난?

  엄연히 다른 것이다. Push 방식은 주체가 관찰자에게 정보를 보내는 것이고 Pull 방식은 관찰자가 주체에게서 정보를 받아오는 것이다.

    - 누가 **능동적 주체**인가에 대한 영역인 것이다.

```
@Override
public void update() {
    this.temperature = this.weatherData.getTemperature();
    this.humidity = this.weatherData.getHumidity();
    this.pressure = this.weatherData.getPressure();
    display();
}
```
