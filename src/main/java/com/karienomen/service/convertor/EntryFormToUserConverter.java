package com.karienomen.service.convertor;

import com.karienomen.model.Address;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.User;
import com.karienomen.model.EntryForm;
import org.springframework.stereotype.Component;

/**
 * Created by andreb on 26.01.17.
 */
@Component
public class EntryFormToUserConverter {

    public static User convert(EntryForm request){

        User user = new User();
        user.setName(request.getName());

        Address address = new Address();
        address.setCountry(request.getCountry());
        address.setCity(request.getCity());
        address.setAddressLine(request.getAddressLine());
        user.setAddress(address);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCode(request.getCode());
        phoneNumber.setPhone(request.getPhone());

        user.getPhones().add(phoneNumber);
        return user;
    }
}
