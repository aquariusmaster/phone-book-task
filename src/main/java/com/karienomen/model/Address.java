package com.karienomen.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andreb on 25.01.17.
 */
@Entity
public class Address implements Serializable{

    @Id @GeneratedValue
    private long addressId;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String addressLine;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        return addressLine.equals(address.addressLine);

    }

    @Override
    public int hashCode() {
        int result = country.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + addressLine.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return country +
                ", " + city +
                ", " + addressLine;
    }
}
