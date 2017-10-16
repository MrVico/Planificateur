package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.AbstractDao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.PersonDao;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.PasswordManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class PersonController {
    private PersonDao personDao = null;
    private static PersonController personController = null;

    private PersonController() {
        try {
            this.personDao = DatabaseDao.getPersonDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PersonController getInstance(){
        if (personController == null)
            personController = new PersonController();
        return personController;
    }

    public Person addPerson(String login, String password, String email, String firstname, String lastname) throws SQLException, IOException {
        return personDao.add(new Person(null, login, password, email, firstname, lastname));
    }

    public Person getPerson(String login) {
        return personDao.get(login);
    }

    public Person getPerson(String login,String password) {
        return personDao.get(login,password);
    }
}
