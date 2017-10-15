package fr.univtln.maxremvi.Database;

import java.sql.*;

public class DatabaseUtil {
    private static String databaseUrl = "jdbc:h2:tcp://localhost/~/planificateur";
    private static String username = "sa";
    private static String password = "";

    private static Connection connection = null;

    // Java SQL
    public static Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(databaseUrl, username, password);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }

    private static PreparedStatement createStatement(String query, Object... parameters) throws SQLException {
        Connection conn = DatabaseUtil.getConnection();

        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 1; i <= parameters.length; i++) {
            stmt.setObject(i, parameters[i - 1]);
        }

        return stmt;
    }

    public static int executeInsert(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    public static ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
