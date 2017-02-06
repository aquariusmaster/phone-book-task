package com.karienomen;

import com.karienomen.controllers.PhonebookController;
import com.karienomen.model.Address;
import com.karienomen.model.EntryForm;
import com.karienomen.model.PhoneNumber;
import com.karienomen.model.User;
import com.karienomen.service.UserService;
import com.karienomen.service.convertor.EntryFormToUserConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static com.karienomen.UserBuilder.userFiller;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by andreb on 29.01.17.
 */
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PhonebookController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void findAll() throws Exception{
        User user1 = userFiller();
        User user2 = userFiller();
        user2.setName("Second");

        List<User> expectedList = asList(user1, user2);

        when(userService.findAll()).thenReturn(expectedList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", expectedList));
    }

    @Test
    public void convertEntryToUserEntryTest() throws Exception{
        User user = userFiller();

        EntryForm entry = new EntryForm();
        entry.setName(user.getName());
        entry.setCountry(user.getAddress().getCountry());
        entry.setCity(user.getAddress().getCity());
        entry.setAddressLine(user.getAddress().getAddressLine());
        PhoneNumber phone = user.getPhones().iterator().next();
        entry.setCode(phone.getCode());
        entry.setPhone(phone.getPhone());

        User convertedUser = EntryFormToUserConverter.convert(entry);

        assertThat(user, equalTo(convertedUser));
        assertThat(user.getAddress(), equalTo(convertedUser.getAddress()));
        assertThat(user.getPhones(), equalTo(convertedUser.getPhones()));
        assertThat(convertedUser.getPhones(), contains(phone));

    }

    @Test
    public void saveEntryTest() throws Exception{
        User user = userFiller();

        //TODO

    }

}
