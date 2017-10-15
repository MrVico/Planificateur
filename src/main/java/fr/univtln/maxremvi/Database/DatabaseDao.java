package fr.univtln.maxremvi.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;

import java.sql.SQLException;

public class DatabaseDao {
    public static Dao<Person, String> getPersonDao() throws SQLException {
        return DaoManager.createDao(DatabaseUtil.getConnectionSource(), Person.class);
    }

    public static Dao<Poll, String> getPollDoa() throws SQLException {
        return DaoManager.createDao(DatabaseUtil.getConnectionSource(), Poll.class);
    }
}
