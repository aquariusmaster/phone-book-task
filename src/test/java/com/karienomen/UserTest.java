package com.karienomen;

import com.karienomen.model.Address;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.User;
import com.karienomen.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static com.karienomen.UserUtil.userFiller;
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

//    @Ignore
    @Test
    public void userSaveTest(){

        User user = userFiller();
        User fetched = userService.save(user);
        assertEquals(fetched, user);

        List<User> list = userService.findAll();
        assertTrue(list.size() == 1);

        User oldUser = userFiller();
        userService.save(user);
        list = userService.findAll();
        assertTrue(list.size() == 1);
    }
//    @Ignore
    @Test
    public void userAddressUpdateTest() {

        User user = userFiller();
        user.getAddress().setCity("Kharkiv");
        User savedUser = userService.save(user);
        assertEquals(user.getAddress(), savedUser.getAddress());
        user.getAddress().setCity("NotCity");
        userService.save(user);
        List<User> list = userService.findAll();
        assertTrue(list.size() == 1);

    }
//    @Ignore
    @Test
    public void userUpdateTest() {

        User user = userService.save(userFiller());

        PhoneNumber newPhone = new PhoneNumber("111", "1234567");
        user.getPhones().add(newPhone);
        User fetched = userService.save(user);
        assertTrue(fetched.getPhones().size() == 2);

        fetched.getAddress().setCountry("USA");
        User fetched2 = userService.save(fetched);
        assertEquals(fetched2.getAddress().getCountry(), "USA");
        assertEquals(fetched2.getAddress().getCity(), userFiller().getAddress().getCity());

        List<User> list = userService.findAll();
        assertEquals(list.size(), 1);
        System.out.println(list);

    }
//    @Ignore
    @Test
    public void phoneNumberTest() {

        Iterator<PhoneNumber> it = userFiller().getPhones().iterator();
        PhoneNumber phone = it.next();

        Iterator<PhoneNumber> it2 = userFiller().getPhones().iterator();
        PhoneNumber phone2 = it2.next();
        phone2.setCode("044");

        assertFalse(phone.hashCode() == phone2.hashCode());
        assertFalse(phone == phone2);

        userFiller().getPhones().add(phone2);

        Address address1 = userFiller().getAddress();
        address1.setCountry("Union");
        assertNotEquals(address1, userFiller().getAddress());
        assertNotEquals(address1, userFiller().getAddress());

    }

    @Test
    public void searchTest() {

        User user = userFiller();
        user.getPhones().add(new PhoneNumber("000", "123456"));
        userService.save(user);

        List<User> searchList = userService.findAll("123456");
        assertEquals(searchList.size(), 1);

        User user2 = userFiller();
        user2.setName("Ivanov");
        userService.save(user2);

        searchList = userService.findAll("www");
        assertEquals(searchList.size(), 0);

        searchList = userService.findAll("16");
        assertEquals(searchList.size(), 2);

    }

}



