package fr.univtln.maxremvi.database;

import java.sql.*;

public class DatabaseUtil {
    private static String[] databaseUrl = {
            "jdbc:h2:tcp://localhost/~/planificateur",
            "jdbc:postgresql://ec2-174-129-218-106.compute-1.amazonaws.com:5432/d1apm2n6n77at5?user=vicsjcjeheltzp&password=5dcd50d5b272e8c5e623a25b12d2d16655f72719c9b09d9aec05a1caf7e4709a&sslmode=require"
    };
    private static String username = "sa";
    private static String password = "";

    private static Connection connection = null;

    // Java SQL
    public static Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(databaseUrl[1], username, password);
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

    public static void executeUpdate(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        stmt.executeUpdate();
    }

    public static ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
