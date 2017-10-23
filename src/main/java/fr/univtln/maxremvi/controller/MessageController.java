package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.MessageDao;
import fr.univtln.maxremvi.model.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageController {
    private MessageDao messageDao = null;
    private static MessageController messageController = null;

    private MessageController() {
        try {
            this.messageDao = DatabaseDao.getMessageDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MessageController getInstance(){
        if(messageController == null)
            return new MessageController();
        else
            return messageController;
    }

    public Message add(Message message) throws SQLException{
        return messageDao.add(message);
    }

    public List<Message> getPollMessages(int pollID) throws SQLException {
        return messageDao.getPollMessages(pollID);
    }
}
