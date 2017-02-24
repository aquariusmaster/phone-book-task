package com.karienomen.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by andreb on 25.01.17.
 */
@Entity
public class PhoneNumber implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String phone;

    public PhoneNumber(){}

    public PhoneNumber(String code, String phone){
        this.code = code;
        this.phone = phone;
    }

    public PhoneNumber(long id, String code, String phone){
        this.id = id;
        this.code = code;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFullAddress(){
        return "(" + code + ") " + phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (!code.equals(that.code)) return false;
        return phone.equals(that.phone);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getFullAddress();
    }

}
