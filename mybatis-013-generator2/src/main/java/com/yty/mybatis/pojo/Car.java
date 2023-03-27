package com.yty.mybatis.pojo;

import java.math.BigDecimal;

public class Car {
    private Integer id;

    private String carnum;

    private String brand;

    private BigDecimal guidePrice;

    private String produceTime;

    private String carType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum == null ? null : carnum.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime == null ? null : produceTime.trim();
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carnum='" + carnum + '\'' +
                ", brand='" + brand + '\'' +
                ", guidePrice=" + guidePrice +
                ", produceTime='" + produceTime + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }
}