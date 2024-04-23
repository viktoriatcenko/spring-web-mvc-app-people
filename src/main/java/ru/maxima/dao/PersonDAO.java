package ru.maxima.dao;

// DAO - Data Access Object

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

    private final String URL = "jdbc:postgresql://localhost:5433/maxima";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    private Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Person> getAllPeople() {

        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQLQuery = "select * " +
                    "from person";

            ResultSet resultSet = statement.executeQuery(SQLQuery);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return people;
    }


    public Person findById(Long id) {
        Person person = new Person();
        try {
//            String SQLQuery = "select * " +
//                    "from person where id = " + id;
            PreparedStatement prepareStatement = connection.prepareStatement("select * from person where id = ?");
            // select * from person where id = 3

            prepareStatement.setLong(1, id);


            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
        Optional<Long> last = getAllPeople().stream()
                .map(Person::getId)
                .max(Comparator.naturalOrder());
        Long id = last.get();

        try {
            Statement statement = connection.createStatement();
            // insert into person (id, name, email, age)
            //            values (1, 'Viktor', 'viktor@mail.ru', 33)

            String SQLQuery = "insert into person (id, name, email, age) values (" + ++id +  ", '"
                    + person.getName() + "', '" + person.getEmail() + "', " + person.getAge() + ") ";

            statement.executeUpdate(SQLQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Person personFromView, Integer id) {
        // update person set name = '', age = 255, email = '' where id = 1;

        try {
            Statement statement = connection.createStatement();

            String SQLQuery = "update person set name = '" + personFromView.getName() + "' ," +
                    "age = " + personFromView.getAge() + ",  email = '" + personFromView.getEmail() +
                    "'  where id = " + id;

            statement.executeUpdate(SQLQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Integer id) {
        // delete from person where id = 1;

        try {
            Statement statement = connection.createStatement();

            String SQLQuery = "delete from person where id = " + id;

            statement.executeUpdate(SQLQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
