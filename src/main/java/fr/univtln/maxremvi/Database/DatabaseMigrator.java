package fr.univtln.maxremvi.Database;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.univtln.maxremvi.Controller.PersonController;
import fr.univtln.maxremvi.Model.Person;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseMigrator {
    private static void createTables() throws SQLException, IOException {
        ConnectionSource connectionSource = DatabaseUtil.getConnectionSource();
        TableUtils.createTable(connectionSource, Person.class);
        DatabaseUtil.closeConnection();
    }

    public static void main(String[] args) {
        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addPerson() {
        PersonController personController = new PersonController();
        personController.addPerson("dzdz", "mguil", "max", "guil", "dzd@guil");
        personController.addPerson("dzd", "mguil", "max", "guil", "dddzd@guil");
    }
}
