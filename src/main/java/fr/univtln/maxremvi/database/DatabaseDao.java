package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Invitation;

import java.sql.SQLException;

public class DatabaseDao {
    public static PersonDao getPersonDao() throws SQLException {
        return (PersonDao) new PersonDao().getInstance();
    }

    public static PollDao getPollDao() throws SQLException {
        return (PollDao) new PollDao().getInstance();
    }

    public static AnswerChoiceDao getAnswerChoiceDao() throws SQLException {
        return (AnswerChoiceDao) new AnswerChoiceDao().getInstance();
    }

    public static AnswerDao getAnswerDao() throws SQLException {
        return (AnswerDao) new AnswerDao().getInstance();
    }

    public static InvitationDao getInvitationDao() throws SQLException {
        return (InvitationDao) new InvitationDao().getInstance();
    }

    public static MessageDao getMessageDao() throws SQLException {
        return (MessageDao) new MessageDao().getInstance();
    }
}
