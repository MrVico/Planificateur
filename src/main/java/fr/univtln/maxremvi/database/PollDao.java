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
            System.out.println("Poll get : "+e.getMessage());
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
            System.out.println("Poll getAll : "+e.getMessage());
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
            System.out.println("Poll getPollPromoterID : "+e.getMessage());
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
            System.out.println("Poll getVisiblePollsForPerson : "+e.getMessage());
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
            System.out.println("Poll getMaxCountAnswer : "+e.getMessage());
            return -1;
        }
        return 0;
    }

    public Poll add(Poll object) {
        try{
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
        catch (SQLException e){
            System.out.println("Poll add : "+e.getMessage());
            return null;
        }
    }

    public List<Poll> addAll(List<Poll> objects) {
        ArrayList<Poll> insertedPolls = new ArrayList<>();
        for (Poll poll : objects) {
            Poll newPoll = this.add(poll);
            if(newPoll == null)
                return null;
            else
                insertedPolls.add(newPoll);
        }
        return insertedPolls;
    }

    public boolean update(Poll object) {
        try {
            String query = "UPDATE POLL SET IDPERSON = ?, TITLE = ?, DESCRIPTION = ?, LOCATION = ?, UPDATEDATE = ?, CLOSED = ?, MULTIPLECHOICE = ?, HIDEANSWERS = ?, ADDDATES = ?, TYPE = ?::polltype WHERE ID = ?";
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
                    object.getType().toString(),
                    object.getID()
            );
            return true;
        } catch (SQLException e) {
            System.out.println("Poll update : "+e.getMessage());
            return false;
        }
    }

    public boolean close(boolean bool, int pollID) {
        try {
            String query = "UPDATE POLL SET CLOSED = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, bool, pollID);
            return true;
        } catch (SQLException e) {
            System.out.println("Poll close : "+e.getMessage());
            return false;
        }
    }

    public boolean remove(int id) {
        try{
            String query = "DELETE FROM POLL WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, id);
            return true;
        }
        catch (SQLException e){
            System.out.println("Poll remove : "+e.getMessage());
            return false;
        }
    }

    public boolean remove(Poll object) {
        return remove(object.getID());
    }

    public boolean setFinalDate(int pollID, Date finalDate) {
        try{
            String query = "UPDATE POLL SET FINALDATE = ? WHERE ID = ?";
            DatabaseUtil.executeUpdate(query, TimeManager.timeToSqlFormat(finalDate), pollID);
            return true;
        }
        catch (SQLException e){
            System.out.println("Poll setFinalDate : "+e.getMessage());
            return false;
        }
    }
}
