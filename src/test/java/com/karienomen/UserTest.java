package com.karienomen;

import com.karienomen.model.Address;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.User;
import com.karienomen.service.UserService;
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
import static org.junit.Assert.*;

/**
 * Created by andreb on 25.01.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Before
    public void deleteData(){
        userService.deleteAll();
    }


    @Test
    public void userSaveTest(){

        User user = userFiller();
        User fetched = userService.save(user);
        assertThat(fetched, equalTo(user));

        List<User> list = userService.findAll();
        assertThat(list, hasSize(1));

        User oldUser = userFiller();
        userService.save(user);
        list = userService.findAll();
        assertThat(list, hasSize(1));
    }

    @Test
    public void userAddressUpdateTest() {

        User user = userFiller();
        user.getAddress().setCity("Kharkiv");
        User savedUser = userService.save(user);
        assertThat(user.getAddress(), samePropertyValuesAs(savedUser.getAddress()));
        user.getAddress().setCity("NotCity");
        userService.save(user);
        List<User> list = userService.findAll();
        assertThat(list, hasSize(1));

    }

    @Test
    public void userUpdateTest() {

        User user = userService.save(userFiller());

        PhoneNumber newPhone = new PhoneNumber("111", "1234567");
        user.getPhones().add(newPhone);
        User fetched = userService.save(user);
        assertThat(fetched.getPhones(), hasSize(2));

        fetched.getAddress().setCountry("USA");
        User fetched2 = userService.save(fetched);
        assertThat(fetched2.getAddress().getCountry(), equalTo("USA"));
        assertThat(fetched2.getAddress().getCity(), equalTo(userFiller().getAddress().getCity()));

        List<User> list = userService.findAll();
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
    public void searchTest() {

        User user = userFiller();
        user.getPhones().add(new PhoneNumber("000", "123456"));
        userService.save(user);

        List<User> searchList = userService.findByFilter("123456");
        assertThat(searchList, hasSize(1));

        User user2 = userFiller();
        user2.setName("Ivanov");
        userService.save(user2);

        searchList = userService.findByFilter("www");
        assertThat(searchList, empty());

        searchList = userService.findByFilter("16");
        assertThat(searchList, hasSize(2));

    }

}



