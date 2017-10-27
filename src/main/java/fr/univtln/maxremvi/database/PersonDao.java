package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao extends AbstractDao<Person> {
    /***
     * @return The instance of the current implementation of this PersonDao
     */
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new PersonDao();
        return instance;
    }

    /***
     * Query the database to retrieve the given Person
     *
     * @param id The Person id
     * @return The recreated Person
     */
    public Person get(int id) {
        try {
            String query = "SELECT * FROM PERSON WHERE ID = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            Person person = Person.fromResultSet(rs);
            rs.close();
            return person;
        } catch (SQLException e) {
            System.out.println("Person get(id) : " + e.getMessage());
        }
        return null;
    }

    /***
     * Query the database in order to retrieve the Person with the given credentials (login + password)
     *
     * @param login The Person's login
     * @param password The Person's password
     * @return The recreated Person if credentials are correct, null if not
     */
    public Person get(String login, String password) {
        try {
            String query = "SELECT * FROM PERSON WHERE LOGIN = ? and PASSWORD = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, login, password);
            if (rs.next()) {
                Person person = Person.fromResultSet(rs);
                rs.close();
                return person;
            }
        } catch (SQLException e) {
            System.out.println("Person get(login, password) : " + e.getMessage());
        }
        return null;
    }

    /***
     * Query the database in order to retrieve the List of all Persons who are not invited go a given Poll, excluding one Person
     *
     * @param pollID The Poll id
     * @param personID The connected Person id
     * @return The List of recreated Persons
     */
    public List<Person> getNotInvitedToPoll(int pollID, int personID) {
        try {
            String query = "SELECT * FROM PERSON WHERE ID NOT IN (SELECT IDPERSON FROM INVITATION WHERE IDPOLL = ?) AND ID != ?" +
                    "AND ID NOT IN (SELECT IDPERSON FROM POLL WHERE ID = ?)";
            ResultSet rs = DatabaseUtil.executeQuery(query, pollID, personID, pollID);

            List<Person> personList = new ArrayList<>();
            while (rs.next()) {
                personList.add(new Person(rs.getInt("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD"),
                        rs.getString("EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME")));
            }
            return personList;
        } catch (SQLException e) {
            System.out.println("Person getNotInvitedToPoll : " + e.getMessage());
        }
        return null;
    }

    public List<Person> getAll() {
        return null;
    }

    /***
     * Stores a given Person into the database
     *
     * @param object The Person to store
     * @return The inserted Person (with his id)
     */
    public Person add(Person object) {
        String query = "INSERT INTO PERSON(LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME) VALUES(?, ?, ?, ?, ?)";
        int personId = 0;
        try {
            personId = DatabaseUtil.executeInsert(query, object.getLogin(), object.getPassword(), object.getEmail(), object.getFirstname(), object.getLastname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ((PersonDao) getInstance()).get(personId);
    }

    /***
     * Stores a list of Persons into the database
     *
     * @param objects The List of Persons to store
     * @return The List of inserted Persons (with their ids)
     */
    public List<Person> addAll(List<Person> objects) {
        ArrayList<Person> insertedPersons = new ArrayList<>();
        for (Person person : objects) {
            insertedPersons.add(this.add(person));
        }
        return insertedPersons;
    }

    /***
     * Update a given Person into the database
     *
     * @param object The Person to update
     * @return true if the update happened successfully, false if not
     */
    public boolean update(Person object) {
        try {
            String query = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, object.getFirstname(), object.getLastname(), object.getID());
            return true;
        } catch (SQLException e) {
            System.out.println("Person update : " + e.getMessage());
            return false;
        }
    }

    /***
     * Update the Person's password
     *
     * @param object The Person to update
     * @return true if the update happened successfully, false if not
     */
    public boolean changePassword(Person object) {
        try {
            String query = "UPDATE PERSON SET PASSWORD = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, object.getPassword(), object.getID());
            return true;
        } catch (SQLException e) {
            System.out.println("Person changePassword : " + e.getMessage());
            return false;
        }
    }

    /***
     * Query the database in order to check if the login is already taken
     *
     * @param login The login to check
     * @return true if the login is already taken, false if not
     */
    public boolean loginTaken(String login) {
        try {
            String query = "SELECT * FROM PERSON WHERE LOGIN = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, login);
            if(rs.next())
                return true;
        } catch (SQLException e) {
            System.out.println("Person loginTaken : " + e.getMessage());
        }
        return false;
    }

    /***
     * Query the database in order to check if the email is already taken
     *
     * @param email The email to check
     * @return true if the email is already taken, false if not
     */
    public boolean emailTaken(String email) {
        try {
            String query = "SELECT * FROM PERSON WHERE EMAIL = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, email);
            if(rs.next())
                return true;
        } catch (SQLException e) {
            System.out.println("Person emailTaken : " + e.getMessage());
        }
        return false;
    }

    /***
     * Removes the Person with the given id from the database
     *
     * @param id The Person id
     * @return true if the delete happened successfully, false if not
     */
    public boolean remove(int id) {
        try {
            String query = "DELETE FROM PERSON WHERE ID = ?";
            DatabaseUtil.executeInsert(query, id);
            return true;
        } catch (SQLException e) {
            System.out.println("Person remove : " + e.getMessage());
            return false;
        }
    }

    /***
     * Removes the given Person from the database
     *
     * @param object The Person to remove
     * @return true if the delete happened successfully, false if not
     */
    public boolean remove(Person object) {
        return remove(object.getID());
    }
}
