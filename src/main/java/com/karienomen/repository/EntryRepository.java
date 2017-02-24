package com.karienomen.repository;

import com.karienomen.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by andreb on 26.01.17.
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>, JpaSpecificationExecutor {

    Entry findByName(String name);

}