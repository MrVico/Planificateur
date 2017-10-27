package fr.univtln.maxremvi.database;

public class DatabaseDao {
    /***
     * @return The PersonDao instance
     */
    public static PersonDao getPersonDao() {
        return (PersonDao) new PersonDao().getInstance();
    }

    /***
     * @return The PollDao instance
     */
    public static PollDao getPollDao() {
        return (PollDao) new PollDao().getInstance();
    }

    /***
     * @return The AnswerChoiceDao instance
     */
    public static AnswerChoiceDao getAnswerChoiceDao() {
        return (AnswerChoiceDao) new AnswerChoiceDao().getInstance();
    }

    /***
     * @return The AnswerDao instance
     */
    public static AnswerDao getAnswerDao() {
        return (AnswerDao) new AnswerDao().getInstance();
    }

    /***
     * @return The InvitationDao instance
     */
    public static InvitationDao getInvitationDao() {
        return (InvitationDao) new InvitationDao().getInstance();
    }

    /***
     * @return The InvitationDao instance
     */
    public static MessageDao getMessageDao() {
        return (MessageDao) new MessageDao().getInstance();
    }
}
