package fr.univtln.maxremvi.database;

import java.sql.*;

public class DatabaseUtil {
    private static String databaseUrl = "jdbc:postgresql://ec2-174-129-218-106.compute-1.amazonaws.com:5432/d1apm2n6n77at5?user=vicsjcjeheltzp&password=5dcd50d5b272e8c5e623a25b12d2d16655f72719c9b09d9aec05a1caf7e4709a&sslmode=require";
    private static Connection connection = null;

    /***
     * If connection variable isn't set, creates it; after that, returns it
     *
     * @return The database connection
     * @throws SQLException If a connection timeout happens
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(databaseUrl);
        return connection;
    }

    /***
     * Closes the connection and erase the variable
     *
     * @throws SQLException If the driver could not access to the database
     */
    public static void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }

    /***
     * Create and return a prepared statement with its string query and its parameters values
     *
     * @param query The string query
     * @param parameters The list of parameter values
     * @return The prepared statement object
     * @throws SQLException If the statement can't be created
     */
    private static PreparedStatement createStatement(String query, Object... parameters) throws SQLException {
        Connection conn = DatabaseUtil.getConnection();

        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 1; i <= parameters.length; i++) {
            stmt.setObject(i, parameters[i - 1]);
        }

        return stmt;
    }

    /***
     * Executes the given insert query with its given parameters and returns the id of the inserted line
     *
     * @param query The insert query to execute
     * @param parameters The parameters for the query
     * @return The id of the inserted line
     * @throws SQLException If the statement can't be created of if an error accessing the database happens
     */
    public static int executeInsert(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();

        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    /***
     * Executes the given update query
     *
     * @param query The update query to execute
     * @param parameters The parameters for the query
     * @throws SQLException If the statement can't be created of if an error accessing the database happens
     */
    public static void executeUpdate(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        stmt.executeUpdate();
    }

    /***
     * Execute the given select query with its given parameters
     *
     * @param query The select query to execute
     * @param parameters The parameters for the query
     * @return The rows of the result
     * @throws SQLException If the statement can't be created of if an error accessing the database happens
     */
    public static ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        PreparedStatement stmt = createStatement(query, parameters);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }
}
