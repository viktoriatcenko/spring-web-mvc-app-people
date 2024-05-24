package ru.maxima;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.models.Person;
import ru.maxima.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepository repository;

    @Autowired
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Person> getAllPeople() {
        List<Person> people = repository.findAll();


        test();

        return people;
    }

    @Transactional
    public Person findById(Long id) {
        Optional<Person> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public void update(Person person, Integer id) {
        person.setId(Long.valueOf(id));
        repository.save(person);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }

    @Transactional
    public void test() {

        List<Person> v = repository.findPeopleByNameLike("V%");

        System.out.println(v);
    }
}
