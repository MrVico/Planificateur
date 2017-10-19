package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try {
            String query = "SELECT * FROM PERSON WHERE LOGIN = ? and PASSWORD = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, login, password);
            if(rs.next()) {
                Person person = Person.fromResultSet(rs);
                rs.close();
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> getAll() {
        try {
            String query = "SELECT * FROM PERSON";
            ResultSet rs = DatabaseUtil.executeQuery(query);

            List<Person> personList = new ArrayList<>();
            while (rs.next()) {
                personList.add(new Person(rs.getInt("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD"),
                        rs.getString("EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME")));
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person add(Person object) throws SQLException {
        String query = "INSERT INTO PERSON(LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME) VALUES(?, ?, ?, ?, ?)";
        int personId = DatabaseUtil.executeInsert(query, object.getLogin(), object.getPassword(), object.getEmail(), object.getFirstname(), object.getLastname());
        return ((PersonDao) getInstance()).get(personId);
    }

    public List<Person> addAll(List<Person> objects) throws SQLException {
        ArrayList<Person> insertedPersons = new ArrayList<>();
        for (Person person : objects) {
            insertedPersons.add(this.add(person));
        }
        return insertedPersons;
    }

    public boolean update(Person object) {
        try {
            String query = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, object.getFirstname(), object.getLastname(), object.getId());
            return true;
        } catch (SQLException e) {
            System.out.println("PersonDao Update : "+e.getMessage());
            return false;
        }
    }

    public boolean remove(int id) throws SQLException {
        String query = "DELETE FROM PERSON WHERE ID = ?";
        DatabaseUtil.executeInsert(query, id);
        return true;
    }

    public boolean remove(Person object) throws SQLException {
        return remove(object.getId());
    }
}
