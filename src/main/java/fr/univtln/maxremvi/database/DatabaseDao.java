package fr.univtln.maxremvi.database;

import java.sql.SQLException;

public class DatabaseDao {
    public static PersonDao getPersonDao() throws SQLException {
        return (PersonDao) new PersonDao().getInstance();
    }

    public static PollDao getPollDao() throws SQLException {
        return (PollDao) new PollDao().getInstance();
    }
}
