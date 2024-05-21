package ru.maxima.dao;

// DAO - Data Access Object


import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maxima.models.Person;
import java.util.List;
import java.util.Objects;


@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Person> getAllPeople() {
        List<Person> people = getSession().createQuery("select p from Person p", Person.class).getResultList();
        return people;
    }

    @Transactional
    public Person findById(Long id) {
        return getSession().get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        getSession().persist(person);
    }

    @Transactional
    public void update(Person person, Integer id) {
        Person personToBeUpdated = getSession().get(Person.class, id);
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setEmail(person.getEmail());
    }

    @Transactional
    public void delete(Integer id) {
        Person personToBeRemoved = getSession().get(Person.class, id);
        getSession().remove(personToBeRemoved);
    }

    private Session getSession() {
        return Objects.requireNonNull(sessionFactory).getCurrentSession();
    }
}
