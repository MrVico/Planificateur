package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by remi on 15/10/2017.
 */
public class PollDao extends AbstractDao<Poll> {
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new PollDao();
        return instance;
    }

    @Override
    public Poll get(int id) {
        return null;
    }

    @Override
    public List<Poll> getAll() {
        try {
            String query = "SELECT * FROM POLL";
            ResultSet rs = DatabaseUtil.executeQuery(query);

            List<Poll> pollList = new ArrayList<>();
            Person pers = new Person(null, "Login", "Password", "email@gmail.com", null, null);
            while (rs.next()) {
                //System.out.println(rs.getString("TITLE")+" "+rs.getString("DESCRIPTION")+" "+rs.getString("LOCATION")+" "+rs.getDate("CLOSINGDATE")+" "+rs.getString("CLOSED"));
                pollList.add(new Poll(rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("LOCATION"), rs.getDate("CLOSINGDATE"),
                        rs.getBoolean("CLOSED"), pers));
            }
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Poll add(Poll object) throws SQLException {
        return null;
    }

    @Override
    public List<Poll> addAll(List<Poll> objects) throws SQLException {
        return null;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Poll object) {
        return false;
    }
}
