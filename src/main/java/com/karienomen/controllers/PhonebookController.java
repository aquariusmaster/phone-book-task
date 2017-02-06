package com.karienomen.controllers;

import com.karienomen.model.User;
import com.karienomen.model.EntryForm;
import com.karienomen.service.UserService;
import com.karienomen.service.convertor.EntryFormToUserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by andreb on 26.01.17.
 */
@Controller
public class PhonebookController {

    private static Logger logger = LoggerFactory.getLogger(PhonebookController.class);

    @Autowired
    private EntryFormToUserConverter entryFormToUserConverter;

    @Autowired
    private UserService userService;

    @ModelAttribute("entryForm")
    public EntryForm constructUser() {
        return new EntryForm();
    }

    @RequestMapping("/")
    public String getList(Model model){
        model.addAttribute("list", userService.findAll());
        return "list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEntry(Model model){
        model.addAttribute("entryForm", constructUser());
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveEntry(@ModelAttribute @Valid EntryForm entryForm,
                              BindingResult result,
                              Model model){
        if (result.hasErrors()){
            logger.error("errors: " + result.getAllErrors().size(), result);
            return "add";
        }
        logger.info("Form fetched: " + entryForm);
        User user = entryFormToUserConverter.convert(entryForm);

        userService.save(user);

        return "success";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String searchTerm, Model model){
        logger.info("Get query filter: " + searchTerm);
        List<User> list = userService.findByFilter(searchTerm);
        model.addAttribute("list", list);
        return "list";
    }
}
