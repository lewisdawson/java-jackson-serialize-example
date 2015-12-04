package com.lewisdawson.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents information about a car.
 *
 * @author Lewis Dawson <lew.dawson@lewdawson.com>
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NestedCarModel that = (NestedCarModel) o;

        return new EqualsBuilder()
                .append(this.trim, that.trim)
                .append(this.engineSize, that.engineSize)
                .append(this.tireSize, that.tireSize)
                .append(this.vin, that.vin)
                .append(this.price, that.price)
                .append(this.mileage, that.mileage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        int initialPrime = 17;
        int multiplierPrime = 31;

        return new HashCodeBuilder(initialPrime, multiplierPrime)
                .append(this.trim)
                .append(this.engineSize)
                .append(this.tireSize)
                .append(this.vin)
                .append(this.price)
                .append(this.mileage)
                .toHashCode();
    }

}
