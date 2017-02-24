package com.karienomen;

import com.karienomen.controllers.PhonebookController;
import com.karienomen.model.Entry;
import com.karienomen.model.EntryForm;
import com.karienomen.model.PhoneNumber;
import com.karienomen.service.EntryService;
import com.karienomen.service.convertor.EntryFormToUserConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.List;

import static com.karienomen.UserBuilder.entryFormFiller;
import static com.karienomen.UserBuilder.userFiller;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by andreb on 29.01.17.
 */
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Mock
    private EntryService entryService;

    @InjectMocks
    private PhonebookController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");


        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void findAll() throws Exception{
        Entry entry1 = userFiller();
        Entry entry2 = userFiller();
        entry2.setName("Second");

        List<Entry> expectedList = asList(entry1, entry2);

        when(entryService.findAll()).thenReturn(expectedList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", expectedList));

        verify(entryService, times(1)).findAll();
    }

    @Test
    public void convertEntryToUserEntryTest() throws Exception{
        Entry user = userFiller();

        EntryForm entry = new EntryForm();
        entry.setName(user.getName());
        entry.setCountry(user.getAddress().getCountry());
        entry.setCity(user.getAddress().getCity());
        entry.setAddressLine(user.getAddress().getAddressLine());
        PhoneNumber phone = user.getPhones().iterator().next();
        entry.setCode(phone.getCode());
        entry.setPhone(phone.getPhone());

        Entry convertedEntry = EntryFormToUserConverter.convert(entry);

        assertThat(user, equalTo(convertedEntry));
        assertThat(user.getAddress(), equalTo(convertedEntry.getAddress()));
        assertThat(user.getPhones(), equalTo(convertedEntry.getPhones()));
        assertThat(convertedEntry.getPhones(), contains(phone));

    }

    @Test
    public void saveWithSuccessEntryTest() throws Exception{
        Entry user = userFiller();
        EntryForm entry = entryFormFiller();

        mockMvc.perform(post("/add")
                .param("name", entry.getName())
                .param("country", entry.getCountry())
                .param("city", entry.getCity())
                .param("addressLine", entry.getAddressLine())
                .param("code", entry.getCode())
                .param("phone", entry.getPhone()))

                .andExpect(status().isOk())
                .andExpect(view().name("success"));

        verify(entryService, times(1)).save(user);
    }

    @Test
    public void saveWithErrorEntryTest() throws Exception{
        Entry user = userFiller();
        EntryForm entry = entryFormFiller();

        mockMvc.perform(post("/add")
                .param("name", "ee") //error 1
                .param("country", entry.getCountry())
                .param("city", "0")  // error 2
                .param("addressLine", entry.getAddressLine())
                .param("code", "")  //error 3 and error 4
                .param("phone", entry.getPhone()))

                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().errorCount(4));

        verify(entryService, times(0)).save(user);

    }

    @Test
    public void searchTest() throws Exception{
        Entry entry1 = userFiller();
        Entry entry2 = userFiller();
        entry2.setName("Second");

        List<Entry> expectedList1 = asList(entry1, entry2);
        List<Entry> expectedList2 = asList(entry2);

        String searchTerm1 = "";
        String searchTerm2 = "co";
        String searchTerm3 = "no_match";

        when(entryService.findByFilter(searchTerm1)).thenReturn(expectedList1);
        when(entryService.findByFilter(searchTerm2)).thenReturn(expectedList2);
        when(entryService.findByFilter(searchTerm3)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/search").param("q", searchTerm1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", expectedList1));

        mockMvc.perform(get("/search").param("q", searchTerm2))
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", expectedList2));

        mockMvc.perform(get("/search").param("q", searchTerm3))
                .andExpect(status().isOk())
                .andExpect(model().attribute("list", Collections.emptyList()));

        verify(entryService, times(1)).findByFilter(searchTerm1);
        verify(entryService, times(1)).findByFilter(searchTerm2);
        verify(entryService, times(1)).findByFilter(searchTerm3);
    }

}
