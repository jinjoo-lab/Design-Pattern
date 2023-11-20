# 데코레이터 패턴

## 구현상황

- 스타벅스의 주문 시스템
    - 수많은 추가 옵션과 파생 음료를 깔끔하게 포용할 수 있는 시스템을 설계하라 !
    - 가격 산출
    - 추가된 옵션에 따른 설명 변경

**기존 시스템**

![Untitled](https://user-images.githubusercontent.com/84346055/284273739-a2ddf291-0d43-4915-be3d-b968e59a36a5.png)

### 일단박죠~!

![Untitled](https://user-images.githubusercontent.com/84346055/284273767-aca35135-0e95-4478-98e8-bafe28eebf58.png)

### 문제상황

- 첨가물의 종류가 많아진다면 → 새로운 메소드를 추가해야 한다.
- 첨가물 가격이 바뀔 때마다 기존 코드를 수정해야 한다.
- 상속받는 음료 중에 우유는 필요하지 않은 음료가 있다고 가정하자 (아이스티)
    - 근데 여전히 우유에 대한 정보를 상속받는다.

→ 위와 같은 설계는 **OCP(개방 - 폐쇠의 원칙)**을 위반한다.

### OCP

> 클래스의 확장에는 열려 있고 변경에는 닫혀 있어야 한다.
>
- 기존 코드에 대한 변경을 최소화하면서 기능을 추가할 수 있도록 설계해야한다.
- 확장
    - 코드의 유연성 증가 (**코드의 수정이 아닌 추가**를 통해 확장하는 것이 정석)
- 변경
    - 객체의 직접적 수정을 제한

## 상속에 대한 고찰

- 객체 지향 프로그래밍에서 상속은 굉장히 강력한 도구이다. 하지만 상속에 대해 생각해 볼 필요가 있다.
1. 상속은 **정적**이다.
    - 런타임 시 기존 객체의 행동을 변경할 수 없다.
    - 그저 상위 클래스 객체를 통해 자식 클래스 객체를 바꿀 수  만 있다.
2. **자식 클래스는 하나의 부모 클래스만 가질 수 있다.**

**결론**

> 즉 우리는 객체의 행동을 동적으로 변경하고 싶다면 **상속**은 좋은 방법이 아닐 수 있다.
>

### 객체의 집합 관계

> 집합 관계란 한 객체가 다른 객체에 대한 참조를 가짐으로서 **일부 작업을 위임**하는 것
>
- 데코레이터 패턴은 이러한 객체의 집합 관계를 응용한 디자인 패턴이다.

![Untitled](https://user-images.githubusercontent.com/84346055/284273772-ee3cc7e8-7eaa-46ec-8834-08956a387d9e.png)

# 데코레이터 패턴

> **객체의 추가 요소를 동적으로 더할 수 있어** 유연한 기능 확장이 가능한 디자인 패턴
>
- 데코레이터는 자신이 장식하는 객체(감싸는 객체)에게 행동을 위임하거나 추가 작업 수행 가능
- 데코레이터의 슈퍼 클래스는 자신이 장식하는 슈퍼클래스와 동일
    - 이를 통해 객체의 집합 관계를 구현하는 것이다 !
- 하나의 객체를 여러 개의 데코레이터로 감쌀 수 있다.

### 구조

![Untitled](https://user-images.githubusercontent.com/84346055/284273774-3db27215-cd65-446c-ba02-9585e92b930a.png)

1. **컴포턴트**
    - 최상위 인터페이스 : 래퍼(데코레이터)들과 래핑되는 클래스의 공통 인터페이스
2. **구상 컴포넌트**
    - 감싸지는 객체 : 기본 행동을 정의
3. **기초 데코레이터**
    - 래핑되는 객체를 참조하기 위한 필드가 있다.
        - 필드의 유형은 **구상 컴포넌트 + 구상 데코레이터 모두 포용**하기 위해 컴포넌트 인터페이스로 선언

![Untitled](https://user-images.githubusercontent.com/84346055/284273778-fd1f3386-6403-4f9f-b4d6-a5fed6710b5d.png)

1. **구상 데코레이터**
    - 컴포턴트들에 동적으로 추가할 수 있는 행동을 정의
    - 오버라이드 기반 (기초 데코레이터 메소드)

### 적용 시기

- 객체의 행동이 런타임 시 변경 + 추가가 많은 경우
- 객체 간의 결합을 통해 새로운 기능이 생성되는 경우

### 장점

- 유연한 기능 확장 가능
    - 런타임 시 동적으로 기능 확장 가능
- 클라이언트의 코드 수정 없이 기능 확장 가능 → **OCP 원칙 준수**
- 구현체가 아닌 인터페이스를 바라본다 → **DIP 원칙 준수**

### 단점

- 데코레이터 제거가 어렵다.
    - 데코레이터 조합 생성 코드에 대한 파악이 어렵다.
- 순서가 의존하는 데코레이터 방식 (A → B → C 순서만 가능해요 !)는 구현하기 어렵다.

---

## 적용

### UML

![Untitled](https://user-images.githubusercontent.com/84346055/284273783-715a47ae-d4fa-4a28-b9dd-57644f6ba120.png)

### TEST

```
public class Main {
    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        System.out.println(beverage.getDescription());
        System.out.println(beverage.getClass());

        beverage = new Milk(beverage);
        System.out.println(beverage.getDescription());
        System.out.println(beverage.cost());

        beverage = new Soy(beverage);
        System.out.println(beverage.getDescription());
        System.out.println(beverage.cost());
    }
}
```

**Result**

```
House Blend :
3.0
House Blend : + MILK 
4.0
House Blend : + MILK  + 두유 
6.0
```
