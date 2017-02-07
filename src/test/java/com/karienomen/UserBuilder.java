package com.karienomen;

import com.karienomen.model.Address;
import com.karienomen.model.EntryForm;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.User;

/**
 * Created by andreb on 29.01.17.
 */
public class UserBuilder {

    public static User userFiller(){
        User user = new User();
        user.setName("Andrey Bobrov");

        Address address = new Address();
        address.setCountry("Ukraine");
        address.setCity("Kyiv");
        address.setAddressLine("Kopernika, 11, 44");
        user.setAddress(address);

        PhoneNumber phoneNumber = new PhoneNumber("066", "2046725");

        user.getPhones().add(phoneNumber);

        return user;
    }

    public static EntryForm entryFormFiller(){

        User user = userFiller();

        EntryForm entry = new EntryForm();
        entry.setName(user.getName());
        entry.setCountry(user.getAddress().getCountry());
        entry.setCity(user.getAddress().getCity());
        entry.setAddressLine(user.getAddress().getAddressLine());
        PhoneNumber phone = user.getPhones().iterator().next();
        entry.setCode(phone.getCode());
        entry.setPhone(phone.getPhone());

        return entry;
    }
}
