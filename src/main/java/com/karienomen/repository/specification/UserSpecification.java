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
    * Create Criteria object that is used to filter all User fields for a given search parameter
    */
    public static Specification<User> searchInAllFields(final String search) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                String containsLikePattern = getContainsLikePattern(search);

                Predicate namePredicate = cb.like(root.get(User_.name), containsLikePattern);

                SetJoin<User, PhoneNumber> setJoin = root.join(User_.phones);
                Predicate phonesPredicate = cb.or(
                        cb.like(setJoin.get(PhoneNumber_.code), containsLikePattern),
                        cb.like(setJoin.get(PhoneNumber_.phone), containsLikePattern));

                Path<Address> userAddress = root.get(User_.address);
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

