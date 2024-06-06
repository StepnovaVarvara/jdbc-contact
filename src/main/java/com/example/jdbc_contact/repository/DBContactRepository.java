package com.example.jdbc_contact.repository;

import com.example.jdbc_contact.dto.Contact;
import com.example.jdbc_contact.exception.ContactNotFoundException;
import com.example.jdbc_contact.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
@RequiredArgsConstructor
public class DBContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        log.info("Call findAll in DBContactRepository");

        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.info("Call findById in DBContactRepository");

        String sql = "SELECT * FROM contact WHERE id = ?";

        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1))
        );

        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.info("Call save in DBContactRepository");

        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contact (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.info("Call update in DBContactRepository");

        Contact currentContact = findById(contact.getId()).orElse(null);
        if (currentContact != null) {
            String sql = "UPDATE contact SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

            return currentContact;
        }

        log.warn("Contact with ID > {} not found", contact.getId());

        throw new ContactNotFoundException("Contact for update not found! ID > " + contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Call deleteById in DBContactRepository");

        String sql = "DELETE FROM contact WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
