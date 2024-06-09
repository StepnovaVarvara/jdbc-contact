package com.example.jdbc_contact.service;

import com.example.jdbc_contact.dto.Contact;
import com.example.jdbc_contact.repository.DBContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final DBContactRepository dbContactRepository;
    @Override
    public List<Contact> findAll() {
        return dbContactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return dbContactRepository.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return dbContactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return dbContactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        dbContactRepository.deleteById(id);
    }
}
