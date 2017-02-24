package com.karienomen.service;

import com.karienomen.model.Entry;

import java.util.List;

/**
 * Created by andreb on 28.01.17.
 */
public interface EntryService {

    Entry findByName(String name);
    Entry save(Entry entry);
    void delete(Entry entry);
    List<Entry> findAll();
    List<Entry> findByFilter(String searchTerm);
    void deleteAll();
}
