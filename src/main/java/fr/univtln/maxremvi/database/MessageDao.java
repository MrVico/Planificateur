package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Message;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageDao extends AbstractDao<Message> {
    /***
     * @return The instance of the current implementation of this MessageDao
     */
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new MessageDao();
        return instance;
    }

    /***
     * Query the database in order to retrieve the MessageDao with the given id
     *
     * @param id The MessageDao id
     * @return The recreated MessageDao
     */
    @Override
    public Message get(int id) {
        try {
            String query = "SELECT * FROM MESSAGE WHERE IDMESSAGE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            return Message.fromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("Message get : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    /***
     * Query the database in order to retrieve all the messages for a given poll
     *
     * @param pollID The poll id for which we want to get the messages
     * @return The List of recreated Messages
     */
    public List<Message> getPollMessages(int pollID) {
        try {
            String query = "SELECT * FROM MESSAGE WHERE IDPOLL = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, pollID);
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(new Message(rs.getInt("IDMESSAGE"), rs.getInt("IDPERSON"), rs.getInt("IDPOLL"), rs.getString("CONTENT"), rs.getTimestamp("CREATIONDATE")));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println("Message getPollMessages : " + e.getMessage());
            return null;
        }
    }

    /***
     * Stores a Message into the database
     *
     * @param message The message to store
     * @return The stored message (with his id)
     */
    @Override
    public Message add(Message message) {
        try {
            String query = "INSERT INTO MESSAGE(IDPOLL, IDPERSON, CONTENT, CREATIONDATE) VALUES(?, ?, ?, ?)";
            int messageID = DatabaseUtil.executeInsert(query, message.getPollID(), message.getSenderID(), message.getContent(), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
            return get(messageID);
        } catch (SQLException e) {
            System.out.println("Message add : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Message> addAll(List<Message> objects) {
        return null;
    }

    @Override
    public boolean update(Message object) {
        return false;
    }

    /***
     * Remove the Message with the given id from the database
     *
     * @param id The Message id
     * @return true if the delete happened successfully, false if not
     */
    @Override
    public boolean remove(int id) {
        try {
            String query = "DELETE FROM MESSAGE WHERE IDMESSAGE = ?";
            DatabaseUtil.executeUpdate(query, id);
            return true;
        } catch (SQLException e) {
            System.out.println("Message remove : " + e.getMessage());
            return false;
        }
    }

    /***
     * Remove the given Message from the database
     *
     * @param object The Message to remove
     * @return true if the delete happened successfully, false if not
     */
    @Override
    public boolean remove(Message object) { return remove(object.getID()); }
}
