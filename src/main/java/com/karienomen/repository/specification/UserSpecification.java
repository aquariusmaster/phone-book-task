package com.karienomen.repository.specification;

import com.karienomen.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


/**
 * Created by andreb on 28.01.17.
 *
 * Specification class use Criteria API for create filter
 */
public class UserSpecification {

    /*
    * Create Criteria object that is used to filter all Entry fields for a given search parameter
    */
    public static Specification<Entry> searchInAllFields(final String searchTerm) {
        return new Specification<Entry>() {
            @Override
            public Predicate toPredicate(Root<Entry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                String containsLikePattern = getContainsLikePattern(searchTerm);
                //Create Predicate for name field Entry object
                Predicate namePredicate = cb.like(root.get(Entry_.name), containsLikePattern);
                //Create Predicate for Inner object PhoneNumber list
                SetJoin<Entry, PhoneNumber> setJoin = root.join(Entry_.phones);
                Predicate phonesPredicate = cb.or(
                        cb.like(setJoin.get(PhoneNumber_.code), containsLikePattern),
                        cb.like(setJoin.get(PhoneNumber_.phone), containsLikePattern));
                //Predicate for Address inner object
                Path<Address> userAddress = root.get(Entry_.address);
                Predicate addressPredicate = cb.or(
                        cb.like(userAddress.get(Address_.country), containsLikePattern),
                        cb.like(userAddress.get(Address_.city), containsLikePattern),
                        cb.like(userAddress.get(Address_.addressLine), containsLikePattern));

                return cb.or(namePredicate, phonesPredicate, addressPredicate);
            }
        };
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm + "%";
        }
    }

    }

