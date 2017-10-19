package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by remi on 15/10/2017.
 */
public class PollDao extends AbstractDao<Poll> {

    public AbstractDao getInstance() {
        if (instance == null)
            instance = new PollDao();
        return instance;
    }

    public Poll get(int id) {
        try {
            String query = "SELECT * FROM POLL WHERE ID = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            Person pers = PersonController.getInstance().getPerson(rs.getInt("IDPERSON"));
            Poll poll = new Poll(rs.getInt("ID"), rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("LOCATION"), rs.getDate("CLOSINGDATE"),
                    rs.getBoolean("CLOSED"), pers, rs.getBoolean("MULTIPLECHOICE"), rs.getBoolean("HIDEANSWERS"), rs.getBoolean("ADDDATES"), Poll.type.valueOf(rs.getString("TYPE")));
            return poll;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Poll> getAll() {
        try {
            String query = "SELECT * FROM POLL";
            ResultSet rs = DatabaseUtil.executeQuery(query);

            List<Poll> pollList = new ArrayList<>();
            while (rs.next()) {
                Person pers = PersonController.getInstance().getPerson(rs.getInt("IDPERSON"));
                pollList.add(new Poll(rs.getInt("ID"), rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("LOCATION"), rs.getDate("CLOSINGDATE"),
                        rs.getBoolean("CLOSED"), pers, rs.getBoolean("MULTIPLECHOICE"), rs.getBoolean("HIDEANSWERS"), rs.getBoolean("ADDDATES"), Poll.type.valueOf(rs.getString("TYPE"))));
            }
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Poll add(Poll object) throws SQLException {
        String query = "INSERT INTO POLL(IDPERSON, TITLE, DESCRIPTION, LOCATION, CREATIONDATE, UPDATEDATE, CLOSINGDATE, CLOSED, MULTIPLECHOICE, HIDEANSWERS, ADDDATES, TYPE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int pollId = DatabaseUtil.executeInsert(
                query,
                object.getPromoter().getId(),
                object.getTitle(),
                object.getDescription(),
                object.getLocation(),
                TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
                TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
                TimeManager.timeToSqlFormat(object.getClosingDate()),
                object.isClosed(),
                object.isMultipleChoice(),
                object.isHideAnswers(),
                object.isAddDates(),
                object.getType().toString()
        );
        return ((PollDao) getInstance()).get(pollId);
    }

    public List<Poll> addAll(List<Poll> objects) throws SQLException {
        ArrayList<Poll> insertedPolls = new ArrayList<>();
        for (Poll poll : objects) {
            insertedPolls.add(this.add(poll));
        }
        return insertedPolls;
    }

    public boolean update(Poll object){
        try {
            String query = "UPDATE POLL SET IDPERSON = ?, TITLE = ?, DESCRIPTION = ?, LOCATION = ?, UPDATEDATE = ?, CLOSINGDATE = ?, CLOSED = ?, TYPE = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query,
                    object.getPromoter().getId(),
                    object.getTitle(),
                    object.getDescription(),
                    object.getLocation(),
                    TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
                    TimeManager.timeToSqlFormat(object.getClosingDate()),
                    object.isClosed(),
                    object.getType().toString());
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean remove(int id) throws SQLException {
        String query = "DELETE FROM POLL WHERE ID = ?";
        DatabaseUtil.executeInsert(query, id);
        return true;
    }

    public boolean remove(Poll object) throws SQLException {
        return remove(object.getId());
    }
}
