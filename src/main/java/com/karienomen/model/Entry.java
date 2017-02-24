package com.karienomen.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by andreb on 25.01.17.
 * Entry class represent Entry
 */
@Entity
public class Entry {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(nullable = false)
    private String name;
    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Address address;
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<PhoneNumber> phones = new HashSet<>();

    public Entry(){}

    public Entry(String name){
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<PhoneNumber> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneNumber> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        return name.equals(entry.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Entry: ID# " + userId +
                ", " + name +
                ", " + address +
                ", Tel.: " + phones;
    }
}
