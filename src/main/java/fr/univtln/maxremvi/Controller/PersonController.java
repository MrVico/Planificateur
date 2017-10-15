package fr.univtln.maxremvi.Controller;

import fr.univtln.maxremvi.Database.AbstractDao;
import fr.univtln.maxremvi.Database.DatabaseDao;
import fr.univtln.maxremvi.Database.PersonDao;
import fr.univtln.maxremvi.Model.Person;
import fr.univtln.maxremvi.Model.Poll;
import fr.univtln.maxremvi.Utils.PasswordManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class PersonController {
    private PersonDao personDao = null;

    public PersonController() {
        try {
            this.personDao = DatabaseDao.getPersonDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Person addPerson(String login, String password, String email, String firstname, String lastname) throws SQLException, IOException {
        return personDao.add(new Person(null, login, password, email, firstname, lastname));
    }

    public Person getPerson(String login) {
        return personDao.get(login);
    }
}
