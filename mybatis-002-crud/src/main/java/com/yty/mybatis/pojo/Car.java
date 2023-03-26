package com.yty.mybatis.pojo;

import java.util.Objects;

/*
*
* @author:yty
* @version:1.0
* @since:1.0
*
* */
public class Car {
    private Long id;
    private Integer carnum;
    private String brand;
    private Double guideMoney;
    private String produceTime;
    private String carType;

    public Car(){};

    public Car(Long id, Integer carnum, String brand, Double guideMoney, String produceTime, String carType) {
        this.id = id;
        this.carnum = carnum;
        this.brand = brand;
        this.guideMoney = guideMoney;
        this.produceTime = produceTime;
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carnum=" + carnum +
                ", brand='" + brand + '\'' +
                ", guideMoney=" + guideMoney +
                ", produceTime='" + produceTime + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(carnum, car.carnum) && Objects.equals(brand, car.brand) && Objects.equals(guideMoney, car.guideMoney) && Objects.equals(produceTime, car.produceTime) && Objects.equals(carType, car.carType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carnum, brand, guideMoney, produceTime, carType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCarnum() {
        return carnum;
    }

    public void setCarnum(Integer carnum) {
        this.carnum = carnum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getGuideMoney() {
        return guideMoney;
    }

    public void setGuideMoney(Double guideMoney) {
        this.guideMoney = guideMoney;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
