package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            Poll poll = Poll.fromResultSet(rs);
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
                pollList.add(Poll.fromResultSet(rs));
            }
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPollPromoterID(int idPoll) {
        try {
            String query = "SELECT IDPERSON FROM POLL WHERE ID = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll);
            if (rs.next()) {
                return rs.getInt("IDPERSON");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Poll> getVisiblePollsForPerson(Person person) {
        int personId = person.getID();
        try {
            String query =
                    "(SELECT * FROM POLL WHERE TYPE = 'PUBLIC' AND (CLOSED = FALSE OR FINALDATE IS NOT NULL))" +
                            "UNION" +
                            " (SELECT POLL.* FROM POLL INNER JOIN INVITATION ON POLL.ID = INVITATION.IDPOLL WHERE INVITATION.IDPERSON = ? " +
                            " AND (POLL.CLOSED = FALSE  OR POLL.FINALDATE IS NOT NULL))" +
                            "UNION" +
                            "(SELECT * FROM POLL WHERE IDPERSON = ?)" +
                            "ORDER BY UPDATEDATE DESC";
            ResultSet rs = DatabaseUtil.executeQuery(query, personId, personId);

            List<Poll> pollList = new ArrayList<>();
            while (rs.next()) {
                pollList.add(Poll.fromResultSet(rs));
            }
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMaxCountAnswer(int idPoll) {
        try {
            String query = "select MAX(c) FROM(select COUNT(*) AS c from answer where idpoll = ? GROUP BY idperson) AS max;";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll);
            if (rs.next()) {
                return rs.getInt("max");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Poll add(Poll object) throws SQLException {
        String query = "INSERT INTO POLL(IDPERSON, TITLE, DESCRIPTION, LOCATION, CREATIONDATE, UPDATEDATE, CLOSED, MULTIPLECHOICE, HIDEANSWERS, ADDDATES, TYPE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::polltype)";
        int pollId = DatabaseUtil.executeInsert(
                query,
                object.getPromoterID(),
                object.getTitle(),
                object.getDescription(),
                object.getLocation(),
                TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
                TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
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

    public boolean update(Poll object) {
        try {
            String query = "UPDATE POLL SET IDPERSON = ?, TITLE = ?, DESCRIPTION = ?, LOCATION = ?, UPDATEDATE = ?, CLOSED = ?, MULTIPLECHOICE = ?, HIDEANSWERS = ?, ADDDATES = ?, TYPE ID = ?";
            DatabaseUtil.executeUpdate(
                    query,
                    object.getPromoterID(),
                    object.getTitle(),
                    object.getDescription(),
                    object.getLocation(),
                    TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()),
                    object.isClosed(),
                    object.isMultipleChoice(),
                    object.isHideAnswers(),
                    object.isAddDates(),
                    object.getType().toString()
            );
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean close(boolean bool, int pollID) {
        try {
            String query = "UPDATE POLL SET CLOSED = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, bool, pollID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) throws SQLException {
        String query = "DELETE FROM POLL WHERE ID = ?";
        DatabaseUtil.executeUpdate(query, id);
        return true;
    }

    public boolean remove(Poll object) throws SQLException {
        return remove(object.getID());
    }

    public boolean setFinalDate(int pollID, Date finalDate) throws SQLException {
        String query = "UPDATE POLL SET FINALDATE = ? WHERE ID = ?";
        DatabaseUtil.executeUpdate(query, TimeManager.timeToSqlFormat(finalDate), pollID);
        return true;
    }
}
