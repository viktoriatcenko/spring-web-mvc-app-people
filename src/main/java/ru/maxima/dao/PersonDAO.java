package ru.maxima.dao;

// DAO - Data Access Object

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }


    public Person findById(Long id) {
        return jdbcTemplate.queryForObject("select * from person where id = ?",
                new Object[]{id} , new PersonMapper());
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(id, name, age, email) values(?, ?, ?, ?)",
                5, person.getName(), person.getAge(), person.getEmail());

    }

    public void update(Person person, Integer id) {
        jdbcTemplate.update("update person set name = ?, age = ?, email = ? where id = ?",
                person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(Integer id) {
        jdbcTemplate.update("delete from person where id = ?", id);
    }
}
