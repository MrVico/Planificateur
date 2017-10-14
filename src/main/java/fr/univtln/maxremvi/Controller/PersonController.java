package fr.univtln.maxremvi.Controller;

import com.j256.ormlite.dao.Dao;
import fr.univtln.maxremvi.Database.DatabaseDao;
import fr.univtln.maxremvi.Database.DatabaseUtil;
import fr.univtln.maxremvi.Model.Person;

import java.io.IOException;
import java.sql.SQLException;

public class PersonController {
    public PersonController() {

    }

    public Person addPerson(String login, String password, String firstname, String lastname, String email) throws IOException, SQLException {
        Dao<Person, String> personDao = null;
        Person person = null;

        personDao = DatabaseDao.getPersonDao();
        person = new Person(login, password, firstname, lastname, email);

        if (person != null)
            personDao.create(person);

        DatabaseUtil.closeConnection();

        return person;
    }

}
