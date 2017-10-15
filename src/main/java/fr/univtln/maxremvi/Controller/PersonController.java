package fr.univtln.maxremvi.controller;

import com.j256.ormlite.dao.Dao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.DatabaseUtil;
import fr.univtln.maxremvi.model.Person;

import java.io.IOException;
import java.sql.SQLException;

public class PersonController {
    public PersonController() {

    }

    public Person addPerson(String login, String password, String email, String firstname, String lastname) throws IOException, SQLException {
        Dao<Person, String> personDao = null;
        Person person = null;

        personDao = DatabaseDao.getPersonDao();
        person = new Person(login, password, email, firstname, lastname);

        if (person != null)
                personDao.create(person);

        DatabaseUtil.closeConnection();

        return person;
    }

}
