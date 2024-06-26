package com.example.jdbc_contact.repository;


import com.example.jdbc_contact.dto.Contact;

import java.util.List;

public interface ContactRepository {

    List<Contact> findAll();
    Contact findById(Long id);
    Contact save(Contact contact);
    Contact update(Contact contact);
    void deleteById(Long id);
}
