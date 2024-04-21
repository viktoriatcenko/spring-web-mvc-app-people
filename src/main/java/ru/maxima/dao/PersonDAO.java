package ru.maxima.dao;

// DAO - Data Access Object

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.maxima.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private Long PEOPLE_COUNT = 0L;
    private List<Person> people;

    // int a = 5;
    int a;

    {
        a = 5;
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Sergey", "sergey@mail.ru", 25)); // age, nationality, sex
        people.add(new Person(++PEOPLE_COUNT, "Pavel","pavel@mail.ru", 25)); // 30, RF, -
        people.add(new Person(++PEOPLE_COUNT, "Tagir", "tagir@mail.ru", 25));
    }

    public List<Person> getAllPeople() {
        return people;
    }


    public Person findById(Long id) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(Person personFromView, Integer id) {
        Person toBeUpdated = findById(Long.valueOf(id));
        toBeUpdated.setName(personFromView.getName());
        toBeUpdated.setEmail(personFromView.getEmail());
        toBeUpdated.setAge(personFromView.getAge());
    }

    public void delete(Integer id) {
        people.removeIf(p -> p.getId().equals(Long.valueOf(id)));
    }
}
