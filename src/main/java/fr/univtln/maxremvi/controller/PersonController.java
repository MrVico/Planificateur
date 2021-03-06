package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.PersonDao;
import fr.univtln.maxremvi.model.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PersonController {
    private PersonDao personDao = null;
    private static PersonController personController = null;

    private PersonController() {
        this.personDao = DatabaseDao.getPersonDao();
    }

    public static PersonController getInstance() {
        if (personController == null)
            personController = new PersonController();
        return personController;
    }

    public Person addPerson(String login, String password, String email, String firstname, String lastname) {
        return personDao.add(new Person(null, login, password, email, firstname, lastname));
    }

    public boolean updatePerson(Person p) {
        return personDao.update(p);
    }

    public boolean changePassword(Person p) {
        return personDao.changePassword(p);
    }

    public List<Person> getNotInvitedToPoll(int pollID, int personID) {
        return personDao.getNotInvitedToPoll(pollID, personID);
    }

    public Person getPerson(int id) {
        return personDao.get(id);
    }

    public Person getPerson(String login, String password) {
        return personDao.get(login, password);
    }

    public boolean loginTaken(String login) {
        return personDao.loginTaken(login);
    }

    public boolean emailTaken(String email) {
        return personDao.emailTaken(email);
    }
}
