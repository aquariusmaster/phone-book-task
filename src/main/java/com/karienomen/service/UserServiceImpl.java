package com.karienomen.service;

import com.karienomen.model.Address;
import com.karienomen.model.Entry;
import com.karienomen.repository.UserRepository;
import com.karienomen.repository.specification.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andreb on 27.01.17.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public Entry findByName(String name){
        Entry returnEntry = userRepository.findByName(name);
        logger.info("Entry was finded by name '\''" + name + "'\': " + returnEntry);
        return returnEntry;
    }

    public Entry save(Entry entry){
        logger.info("Entry for save: " + entry);

        Entry fetchedEntry = userRepository.findByName(entry.getName());
        Entry returnEntry = null;
        if (fetchedEntry != null){
            //update address
            Address updateAddress = fetchedEntry.getAddress();
            updateAddress.setCountry(entry.getAddress().getCountry());
            updateAddress.setCity(entry.getAddress().getCity());
            updateAddress.setAddressLine(entry.getAddress().getAddressLine());
            //add new PhoneNumber (update is not expected in the task!)
            fetchedEntry.getPhones().addAll(entry.getPhones());
            returnEntry = userRepository.save(fetchedEntry);
            logger.info("Entry updated: " + returnEntry);
            return returnEntry;
        }
        returnEntry = userRepository.save(entry);
        logger.info("Entry created: " + returnEntry);
        return returnEntry;
    }

    public void delete(Entry entry){
        logger.info("Delete entry" + entry);
        userRepository.delete(entry);
    }

    public List<Entry> findAll() {
        return userRepository.findAll();
    }

    public List<Entry> findByFilter(String searchTerm){

        List<Entry> fetchedList = userRepository.findAll(Specifications.where(UserSpecification.searchInAllFields(searchTerm)));
        //filter redundant values for fetchedList
        Set<Entry> entrySet = new HashSet(fetchedList);
        List<Entry> entries = new ArrayList<>(entrySet);
        logger.info("Fetched entries with query '" + searchTerm + "': " + entries);
        return entries;
    }

    public void deleteAll(){
        logger.info("Delete all");
        userRepository.deleteAll();
    }

}
