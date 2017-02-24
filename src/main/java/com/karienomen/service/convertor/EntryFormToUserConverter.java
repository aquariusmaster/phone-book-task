package com.karienomen.service.convertor;

import com.karienomen.model.Address;
import com.karienomen.model.Entry;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.EntryForm;
import org.springframework.stereotype.Component;

/**
 * Created by andreb on 26.01.17.
 */
@Component
public class EntryFormToUserConverter {

    public static Entry convert(EntryForm request){

        Entry entry = new Entry();
        entry.setName(request.getName());

        Address address = new Address();
        address.setCountry(request.getCountry());
        address.setCity(request.getCity());
        address.setAddressLine(request.getAddressLine());
        entry.setAddress(address);

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCode(request.getCode());
        phoneNumber.setPhone(request.getPhone());

        entry.getPhones().add(phoneNumber);
        return entry;
    }
}
