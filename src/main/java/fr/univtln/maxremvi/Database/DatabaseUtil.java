package fr.univtln.maxremvi.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseUtil {
    private static String databaseUrl = "jdbc:h2:tcp://localhost/~/planificateur";
    private static String username = "sa";
    private static String password = "";

    private static ConnectionSource connectionSource = null;

    public static ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null)
            connectionSource = new JdbcConnectionSource(databaseUrl, username, password);
        return connectionSource;
    }

    public static void closeConnection() throws IOException {
        connectionSource.close();
        connectionSource = null;
    }
}
