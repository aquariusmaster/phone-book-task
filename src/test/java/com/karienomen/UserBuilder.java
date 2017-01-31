package com.karienomen;

import com.karienomen.model.Address;
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
        address.setAddressLine("Kopernika, 16B, 48");
        user.setAddress(address);

        PhoneNumber phoneNumber = new PhoneNumber("066", "2046725", user);

        user.getPhones().add(phoneNumber);

        return user;
    }
}
