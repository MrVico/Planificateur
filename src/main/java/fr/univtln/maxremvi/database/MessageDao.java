package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Message;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageDao extends AbstractDao<Message>{
    @Override
    public AbstractDao getInstance() {
        if(instance == null)
            instance = new MessageDao();
        return instance;
    }

    @Override
    public Message get(int id) {
        try {
            String query = "SELECT * FROM MESSAGE WHERE IDMESSAGE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            return Message.fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Message> getAll() throws SQLException {
        return null;
    }

    public List<Message> getPollMessages(int pollID) throws SQLException {
        String query = "SELECT * FROM MESSAGE WHERE IDPOLL = ?";
        ResultSet rs = DatabaseUtil.executeQuery(query, pollID);
        List<Message> messages = new ArrayList<>();
        while(rs.next()){
            messages.add(new Message(rs.getInt("IDMESSAGE"), rs.getInt("IDPERSON"), rs.getInt("IDPOLL"), rs.getString("CONTENT"), rs.getTimestamp("CREATIONDATE")));
        }
        return messages;
    }

    @Override
    public Message add(Message message) throws SQLException {
        String query = "INSERT INTO MESSAGE(IDPOLL, IDPERSON, CONTENT, CREATIONDATE) VALUES(?, ?, ?, ?)";
        int messageID = DatabaseUtil.executeInsert(query, message.getPollID(), message.getSenderID(), message.getContent(), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
        return get(messageID);
    }

    @Override
    public List<Message> addAll(List<Message> objects) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Message object) {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Message object) throws SQLException {
        return false;
    }
}
