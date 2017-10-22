package fr.univtln.maxremvi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {
    private Integer ID;
    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;

    public Person(Integer ID, String login, String password, String email, String firstname, String lastname) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static Person fromResultSet(ResultSet rs) throws SQLException {
        return new Person(
                rs.getInt("ID"),
                rs.getString("LOGIN"),
                rs.getString("PASSWORD"),
                rs.getString("EMAIL"),
                rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"));

    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
