package com.karienomen;

import com.karienomen.model.Address;
import com.karienomen.model.Entry;
import com.karienomen.model.PhoneNumber;
import com.karienomen.service.EntryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static com.karienomen.UserBuilder.userFiller;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by andreb on 25.01.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntryTest {

    @Autowired
    private EntryService entryService;

    @Before
    public void deleteData(){
        entryService.deleteAll();
    }


    @Test
    public void userSaveTest(){

        Entry entry = userFiller();
        Entry fetched = entryService.save(entry);
        assertThat(fetched, equalTo(entry));

        List<Entry> list = entryService.findAll();
        assertThat(list, hasSize(1));

        Entry oldEntry = userFiller();
        entryService.save(entry);
        list = entryService.findAll();
        assertThat(list, hasSize(1));

    }

    @Test
    public void userAddressUpdateTest() {

        Entry entry = userFiller();
        entry.getAddress().setCity("Kharkiv");
        Entry savedEntry = entryService.save(entry);
        assertThat(entry.getAddress(), samePropertyValuesAs(savedEntry.getAddress()));
        entry.getAddress().setCity("NotCity");
        entryService.save(entry);
        List<Entry> list = entryService.findAll();
        assertThat(list, hasSize(1));

    }

    @Test
    public void userUpdateTest() {

        Entry entry = entryService.save(userFiller());

        PhoneNumber newPhone = new PhoneNumber("111", "1234567");
        entry.getPhones().add(newPhone);
        Entry fetched = entryService.save(entry);
        assertThat(fetched.getPhones(), hasSize(2));

        fetched.getAddress().setCountry("USA");
        Entry fetched2 = entryService.save(fetched);
        assertThat(fetched2.getAddress().getCountry(), equalTo("USA"));
        assertThat(fetched2.getAddress().getCity(), equalTo(userFiller().getAddress().getCity()));

        List<Entry> list = entryService.findAll();
        assertThat(list, hasSize(1));

    }

    @Test
    public void phoneNumberEntityTest() {

        Iterator<PhoneNumber> it = userFiller().getPhones().iterator();
        PhoneNumber phone = it.next();

        Iterator<PhoneNumber> it2 = userFiller().getPhones().iterator();
        PhoneNumber phone2 = it2.next();
        phone2.setCode("044");

        assertThat(phone.hashCode(), not(phone2.hashCode()));
        assertThat(phone, not(phone2));

    }

    @Test
    public void addressEntityTest() {

        Address address1 = userFiller().getAddress();
        address1.setCountry("Union");
        assertThat(address1, not(userFiller().getAddress()));

    }

    @Test
    public void searchOneResultTest() {

        Entry entry = userFiller();
        entry.getPhones().add(new PhoneNumber("000", "123456"));
        entryService.save(entry);

        List<Entry> searchList = entryService.findByFilter(entry.getName());
        assertThat(searchList, hasSize(1));

        searchList = entryService.findByFilter(entry.getAddress().getCountry());
        assertThat(searchList, hasSize(1));

        searchList = entryService.findByFilter(entry.getAddress().getCity());
        assertThat(searchList, hasSize(1));

        searchList = entryService.findByFilter(entry.getAddress().getAddressLine());
        assertThat(searchList, hasSize(1));

        entryService.findByFilter("123456");
        assertThat(searchList, hasSize(1));

        entryService.findByFilter("00");
        assertThat(searchList, hasSize(1));

    }

    @Test
    public void searchAllResultTest() {

        Entry entry1 = userFiller();
        Entry entry2 = userFiller();
        entry2.setName("Ivanov");
        entryService.save(entry1);
        entryService.save(entry2);

        assertThat(entryService.findByFilter(""), hasItems(entry1, entry2));
        assertThat(entryService.findByFilter(""), hasSize(2));
    }

    @Test
    public void searchEmptyResultTest() {

        Entry entry = userFiller();
        entryService.save(entry);

        assertThat(entryService.findByFilter("no_match"), empty());

    }

}



