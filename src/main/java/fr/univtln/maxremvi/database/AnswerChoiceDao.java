package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.controller.AnswerController;
import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.utils.TimeManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnswerChoiceDao extends AbstractDao<AnswerChoice> {

    /***
     * @return The instance of the current implementation of this AnswerChoiceDao
     */
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new AnswerChoiceDao();
        return instance;
    }

    /***
     * Query the database in order to retrieve the AnswerChoice with the given id
     *
     * @param id The AnswerChoice id
     * @return The recreated AnswerChoice
     */
    @Override
    public AnswerChoice get(int id) {
        try {
            String query = "SELECT * FROM ANSWERCHOICE WHERE IDANSWERCHOICE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            AnswerChoice answerChoice = AnswerChoice.fromResultSet(rs);
            rs.close();
            return answerChoice;
        } catch (SQLException e) {
            System.out.println("AnswerChoice get : " + e.getMessage());
            return null;
        }
    }

    /***
     * Query the database in order to retrieve all the AnswerChoices for the given poll
     *
     * @param idPoll The poll id
     * @return The List of recreated AnswerChoices
     */
    public List<AnswerChoice> getPollAnswerChoices(int idPoll) {
        try {
            String query = "\n" +
                    "SELECT ac.*, count(a.IDPOLL) TIMESCHOSEN FROM ANSWER a RIGHT JOIN ANSWERCHOICE ac ON a.IDANSWERCHOICE = ac.IDANSWERCHOICE AND a.IDPOLL = ac.IDPOLL " +
                    "WHERE ac.IDPOLL = ? GROUP BY ac.IDANSWERCHOICE ORDER BY ac.DATECHOICE ASC";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll);
            List<AnswerChoice> answerChoices = new ArrayList<>();
            while (rs.next()) {
                answerChoices.add(new AnswerChoice(rs.getInt("IDANSWERCHOICE"), rs.getTimestamp("CREATIONDATE"),
                        rs.getTimestamp("DATECHOICE"), rs.getInt("IDPOLL"), rs.getString("TIMESCHOSEN")));
            }
            return answerChoices;
        } catch (SQLException e) {
            System.out.println("AnswerChoice getPollAnswerChoices : " + e.getMessage());
            return null;
        }
    }

    /***
     * Query the database in order to retrieve all the AnswerChoices for the given person and the given poll
     *
     * @param idPoll The id of the poll
     * @param idPerson The id of the person
     * @return The List of recreated AnswerChoices
     */
    public List<AnswerChoice> getPollAnswerChoicesForPerson(int idPoll, int idPerson) {
        try {
            String query = "SELECT ac.* FROM ANSWER a JOIN ANSWERCHOICE ac ON a.IDANSWERCHOICE = ac.IDANSWERCHOICE WHERE a.IDPOLL = ? AND a.IDPERSON = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll, idPerson);
            List<AnswerChoice> answerChoices = new ArrayList<>();
            while (rs.next()) {
                answerChoices.add(new AnswerChoice(rs.getInt("IDANSWERCHOICE"), rs.getTimestamp("CREATIONDATE"), rs.getTimestamp("DATECHOICE"), rs.getInt("IDPOLL")));
            }
            return answerChoices;
        } catch (SQLException e) {
            System.out.println("AnswerChoice getPollAnswerChoicesForPerson : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<AnswerChoice> getAll() {
        return null;
    }

    /***
     * Stores the given AnswerChoice into the database
     *
     * @param object The AnswerChoice to store
     * @return The stored AnswerChoice (with his id)
     */
    @Override
    public AnswerChoice add(AnswerChoice object) {
        try {
            String query = "INSERT INTO ANSWERCHOICE(DATECHOICE, CREATIONDATE, IDPOLL) VALUES(?, ?, ?)";
            int answerChoiceId = DatabaseUtil.executeInsert(query, TimeManager.timeToSqlFormat(object.getDateChoice()), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()), object.getPollID());
            object.setID(answerChoiceId);
            return object;
        } catch (SQLException e) {
            System.out.println("AnswerChoice add : " + e.getMessage());
            return null;
        }
    }

    /***
     * Stores a List of AnswerChoices into the database
     *
     * @param objects The List of AnswerChoices to store
     * @return The List of inserted AnswerChoices (with ids)
     */
    @Override
    public List<AnswerChoice> addAll(List<AnswerChoice> objects) {
        List<AnswerChoice> insertedAnswerChoices = new ArrayList<>();
        for (AnswerChoice answerChoice : objects) {
            AnswerChoice newAnswerChoice = add(answerChoice);
            if (newAnswerChoice == null)
                return null;
            else
                insertedAnswerChoices.add(newAnswerChoice);
        }
        return insertedAnswerChoices;
    }

    /***
     * Stores and AnswerChoice and store the Answer in the database
     *
     * @param personID The person id to link the selected AnswerChoice
     * @param answerChoice The AnswerChoice to store
     * @return true if everything was stored, false if not
     */
    public boolean addAndAnswer(int personID, AnswerChoice answerChoice) {
        AnswerChoice insertedAnswerChoice = add(answerChoice);
        if (insertedAnswerChoice == null)
            return false;
        Answer newAnswer = AnswerController.getInstance().addAnswer(personID, insertedAnswerChoice.getPollID(), insertedAnswerChoice.getID());
        if (newAnswer == null)
            return false;
        return true;
    }

    @Override
    public boolean update(AnswerChoice object) {
        return false;
    }

    /***
     * Removes the AnswerChoice with the given id
     *
     * @param id The AnswerChoice id
     * @return true if the delete happened successfully, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            String query = "DELETE FROM ANSWERCHOICE WHERE IDANSWERCHOICE = ?";
            DatabaseUtil.executeUpdate(query, id);
            return true;
        } catch (SQLException e) {
            System.out.println("AnswerChoice remove : " + e.getMessage());
            return false;
        }
    }

    /***
     * Removes the given AnswerChoice from the database
     *
     * @param object The AnswerChoice to remove
     * @return true if the delete happened successfully, false if not
     */
    @Override
    public boolean remove(AnswerChoice object) {
        return remove(object.getID());
    }
}
