package com.karienomen.controllers;

import com.karienomen.model.Entry;
import com.karienomen.model.EntryForm;
import com.karienomen.service.EntryService;
import com.karienomen.service.convertor.EntryFormToUserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by andreb on 26.02.17.
 */
@RestController
@RequestMapping("/entry")
public class RestEntryController {

    private static Logger logger = LoggerFactory.getLogger(PhonebookController.class);

    @Autowired
    private EntryService entryService;

    @ModelAttribute("entryForm")
    public EntryForm constructUser() {
        return new EntryForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Entry> getList(){
        List<Entry> fetched = entryService.findAll();
        return fetched;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Entry getEntryById(@PathVariable(value = "id", required = false) long entryId){
        Entry fetched = entryService.findOne(entryId);
        logger.info("Entry fetched: " + fetched);
        if (fetched == null){
            return new Entry(){
                public String getError(){
                    return String.format("Entry #%d not found", entryId);
                }
            };
        }
        return fetched;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Entry saveEntry(@ModelAttribute @Valid EntryForm entryForm,
                            BindingResult result){
        if (result.hasErrors()){
            logger.error("errors: " + result.getAllErrors().size(), result);
            return new Entry(){
                public String getError(){
                    return result.toString();
                }
            };
        }
        logger.info("Form fetched: " + entryForm);
        Entry entry = EntryFormToUserConverter.convert(entryForm);

        entryService.save(entry);

        return entry;
    }

    @RequestMapping("/search")
    public List<Entry> search(@RequestParam(value = "q", required = false) String searchTerm, Model model){
        logger.info("Get query filter: " + searchTerm);
        if(searchTerm == null){
            return getList();
        }else{
            return entryService.findByFilter(searchTerm);
        }

    }
}
