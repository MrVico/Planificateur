package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.utils.PasswordManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by remi on 15/10/2017.
 */
public class PersonDao extends AbstractDao<Person> {

    public AbstractDao getInstance() {
        if (instance == null)
            instance = new PersonDao();
        return instance;
    }

    public Person get(int id) {
        try {
            String query = "SELECT * FROM PERSON WHERE ID = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            Person person = Person.fromResultSet(rs);
            rs.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person get(String login) {
        try {
            String query = "SELECT * FROM PERSON WHERE LOGIN = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, login);
            rs.next();
            Person person = Person.fromResultSet(rs);
            rs.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person get(String login,String password) {
        System.out.println(password);
        try {
            String query = "SELECT * FROM PERSON WHERE LOGIN = ? and PASSWORD = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, login, password);
            rs.next();
            Person person = Person.fromResultSet(rs);
            rs.close();
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> getAll() {
        return null;
    }

    public Person add(Person object) throws SQLException {
        String query = "INSERT INTO PERSON(LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME) VALUES(?, ?, ?, ?, ?)";
        int personId = DatabaseUtil.executeInsert(query, object.getLogin(), object.getPassword(), object.getEmail(), object.getFirstname(), object.getLastname());
        return ((PersonDao) getInstance()).get(personId);
    }

    public List<Person> addAll(List<Person> objects) throws SQLException {
        for (Person person : objects) {
            add(person);
        }
        return objects;
    }

    //Débile parce que ça renvoie toujours true à part si ça plante...
    public boolean update(Person object) throws SQLException {
        String query = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";
        DatabaseUtil.executeUpdate(query, object.getFirstname(), object.getLastname(), object.getId());
        return true;
    }

    public boolean remove(int id) {
        return false;
    }

    public boolean remove(Person object) {
        return false;
    }
}
