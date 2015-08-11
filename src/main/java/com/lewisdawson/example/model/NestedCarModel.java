package com.lewisdawson.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents information about a car.
 *
 * @author Lewis Dawson <ldawson7777@yahoo.com>
 */
public class NestedCarModel {

    @JsonProperty("trim")
    private String trim;

    @JsonProperty("engineSize")
    private String engineSize;

    @JsonProperty("tireSize")
    private String tireSize;

    @JsonProperty("vin")
    private String vin;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("mileage")
    private Integer mileage;

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public String getTireSize() {
        return tireSize;
    }

    public void setTireSize(String tireSize) {
        this.tireSize = tireSize;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
}
