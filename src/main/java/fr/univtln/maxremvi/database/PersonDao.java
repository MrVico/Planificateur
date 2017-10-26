package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Person get(id) : "+e.getMessage());
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
            System.out.println("Person get(login, password) : "+e.getMessage());
        }
        return null;
    }

    public List<Person> getNotInvitedToPoll(int pollID, int personID){
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
            System.out.println("Person getNotInvitedToPoll : "+e.getMessage());
        }
        return null;
    }

    public List<Person> getAll() {
        return null;
    }

    public Person add(Person object) throws SQLException{
        String query = "INSERT INTO PERSON(LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME) VALUES(?, ?, ?, ?, ?)";
        int personId = DatabaseUtil.executeInsert(query, object.getLogin(), object.getPassword(), object.getEmail(), object.getFirstname(), object.getLastname());
        return ((PersonDao) getInstance()).get(personId);
    }

    public List<Person> addAll(List<Person> objects) {
        ArrayList<Person> insertedPersons = new ArrayList<>();
        for (Person person : objects) {
            try{
                insertedPersons.add(this.add(person));
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        return insertedPersons;
    }

    public boolean update(Person object) {
        try {
            String query = "UPDATE PERSON SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, object.getFirstname(), object.getLastname(), object.getID());
            return true;
        } catch (SQLException e) {
            System.out.println("Person update : "+e.getMessage());
            return false;
        }
    }

    public boolean changePassword(Person object) {
        try {
            String query ="UPDATE PERSON SET PASSWORD = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query,object.getPassword(),object.getID());
            return true;
        }catch (SQLException e) {
            System.out.println("Person changePassword : "+e.getMessage());
            return false;
        }
    }

    public boolean remove(int id) {
        try{
            String query = "DELETE FROM PERSON WHERE ID = ?";
            DatabaseUtil.executeInsert(query, id);
            return true;
        }
        catch (SQLException e){
            System.out.println("Person remove : "+e.getMessage());
            return false;
        }
    }

    public boolean remove(Person object) {
        return remove(object.getID());
    }
}
