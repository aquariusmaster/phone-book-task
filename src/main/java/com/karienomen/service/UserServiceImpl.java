package com.karienomen.service;

import com.karienomen.model.Address;
import com.karienomen.model.User;
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

    public User findByName(String name){
        User returnUser = userRepository.findByName(name);
        logger.info("User was finded by name '\''" + name + "'\': " + returnUser);
        return returnUser;
    }

    public User save(User user){
        logger.info("User for save: " + user);

        User fetchedUser = userRepository.findByName(user.getName());
        User returnUser = null;
        if (fetchedUser != null){
            //update address
            Address updateAddress = fetchedUser.getAddress();
            updateAddress.setCountry(user.getAddress().getCountry());
            updateAddress.setCity(user.getAddress().getCity());
            updateAddress.setAddressLine(user.getAddress().getAddressLine());
            //add new PhoneNumber (update is not expected in the task!)
            fetchedUser.getPhones().addAll(user.getPhones());
            returnUser = userRepository.save(fetchedUser);
            logger.info("User updated: " + returnUser);
            return returnUser;
        }
        returnUser = userRepository.save(user);
        logger.info("User created: " + returnUser);
        return returnUser;
    }

    public void delete(User user){
        logger.info("Delete user" + user);
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByFilter(String query){

        List<User> tempList = userRepository.findAll(Specifications.where(UserSpecification.searchInAllFields(query)));
        Set<User> set = new HashSet(tempList);
        List<User> returnList = new ArrayList<>(set);
        logger.info("Fetched entries with query '\'" + query + "'\': " + returnList);
        return returnList;
    }

    public void deleteAll(){
        logger.info("Delete all");
        userRepository.deleteAll();
    }

}
