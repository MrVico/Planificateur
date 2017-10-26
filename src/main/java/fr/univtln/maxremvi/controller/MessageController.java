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
        this.messageDao = DatabaseDao.getMessageDao();
    }

    public static MessageController getInstance() {
        if (messageController == null)
            return new MessageController();
        else
            return messageController;
    }

    public Message add(Message message) {
        return messageDao.add(message);
    }

    public List<Message> getPollMessages(int pollID) {
        return messageDao.getPollMessages(pollID);
    }

    public boolean delete(int messageID) {
        return messageDao.remove(messageID);
    }
}
