package com.example.jdbc_contact.repository;

import com.example.jdbc_contact.dto.Contact;
import com.example.jdbc_contact.exception.ContactNotFoundException;
import com.example.jdbc_contact.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class DBContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact findById(Long id) {
        String sql = "SELECT * FROM contact WHERE id = ?";

        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1))
        );

        return contact;
    }

    @Override
    public Contact save(Contact contact) {
        contact.setId(System.currentTimeMillis());

        String sql = "INSERT INTO contact (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        Contact currentContact = findById(contact.getId());
        if (currentContact != null) {
            String sql = "UPDATE contact SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());

            return currentContact;
        }

        throw new ContactNotFoundException("Contact for update not found! ID > " + contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM contact WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
