package fr.univtln.maxremvi.Controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.univtln.maxremvi.Model.*;

import java.io.IOException;
import java.sql.SQLException;

public class PersonController {
    public PersonController() {

    }

    public Person addPerson(String login, String password, String firstname, String lastname,String email) {
        String databaseUrl = "jdbc:h2:tcp://localhost/~/Planificateur";
        ConnectionSource connectionSource=null;
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl,"root","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Dao<Person, String> personDao;
        Person person;
        /*
        try {
            TableUtils.createTable(connectionSource, Person.class);
            TableUtils.createTable(connectionSource, Poll.class);
            TableUtils.createTable(connectionSource, Answer.class);
            TableUtils.createTable(connectionSource, AnswerChoice.class);
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, Invitation.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        try {
            personDao = DaoManager.createDao(connectionSource, Person.class);
            person = new Person(login, password, firstname, lastname,email);

            personDao.create(person);
            connectionSource.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
