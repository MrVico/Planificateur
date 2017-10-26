package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class AnswerDao extends AbstractDao<Answer> {
    /***
     * @return The instance of the current implementation of this AnswerDao
     */
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new AnswerDao();
        return instance;
    }

    @Override
    public Answer get(int id) {
        return null;
    }

    /***
     * Checks if the given Answer object is already stored into the database
     *
     * @param answer The Answer to check
     * @return true if the Answer is already stored, false if not
     */
    public boolean answerExists(Answer answer) {
        try {
            String query = "SELECT * FROM ANSWER WHERE IDPOLL = ? AND IDPERSON = ? AND IDANSWERCHOICE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, answer.getPollID(), answer.getPersonID(), answer.getAnswerChoiceID());
            return (rs.next());
        } catch (SQLException e) {
            System.out.println("Answer answerExists : " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Answer> getAll() {
        return null;
    }

    /***
     * Stores the given Answer into the database
     *
     * @param object The Answer to store
     * @return The stored Answer (with his id)
     */
    @Override
    public Answer add(Answer object) {
        try {
            if (!answerExists(object)) {
                String query = "INSERT INTO ANSWER(IDPOLL, IDPERSON, IDANSWERCHOICE, CREATIONDATE) VALUES(?, ?, ?, ?)";
                DatabaseUtil.executeUpdate(query, object.getPollID(), object.getPersonID(), object.getAnswerChoiceID(), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
                return object;
            }
        } catch (SQLException e) {
            System.out.println("Answer add : " + e.getMessage());
        }
        return null;
    }

    /***
     * Stores a List of Answer into the database
     *
     * @param objects The List of Answers to store
     * @return The List of the inserted Answers with their ids
     */
    @Override
    public List<Answer> addAll(List<Answer> objects) {
        for (Answer answer : objects) {
            if (add(answer) == null)
                return null;
        }
        return objects;
    }

    /***
     * Removes an Answer from the database
     *
     * @param idPoll The id of the Answer's Poll
     * @param idPerson The id of the author of the Answer
     * @param id The id of the AnswerChoice linked to the Answer
     * @return true if the delete happened successfully, false if not
     */
    public boolean delete(int idPoll, int idPerson, Integer id) {
        try {
            String query = "DELETE FROM ANSWER WHERE IDPOLL = ? AND IDPERSON = ? AND IDANSWERCHOICE = ?";
            DatabaseUtil.executeUpdate(query, idPoll, idPerson, id);
            return true;
        } catch (SQLException e) {
            System.out.println("Answer delete : " + e.getMessage());
            return false;
        }
    }

    /***
     * Remove all Answers of the database for the given Poll, Person and List of AnswerChoice ids
     *
     * @param idPoll The id of the Answer's Poll
     * @param idPerson The id of the author of the Answer
     * @param answerIDs The List of ids of the AnswerChoices linked to the Answer
     * @return true if the deletes happened successfully, false if not
     */
    public boolean deleteAll(int idPoll, int idPerson, List<Integer> answerIDs) {
        for (Integer id : answerIDs) {
            if (!delete(idPoll, idPerson, id))
                return false;
        }
        return true;
    }

    @Override
    public boolean update(Answer object) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Answer object) {
        return false;
    }
}
