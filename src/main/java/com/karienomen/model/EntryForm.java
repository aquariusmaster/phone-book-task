package com.karienomen.model;

import javax.validation.constraints.Size;

/**
 * Created by andreb on 26.01.17.
 * DTO use for build User object from Form
 */
public class EntryForm {

    @Size(min = 3)
    private String name;
    @Size(min = 3)
    private String country;
    @Size(min = 3)
    private String city;
    @Size(min = 5)
    private String addressLine;
    @Size(min = 3)
    private String code;
    @Size(min = 5)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "EntryForm{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
