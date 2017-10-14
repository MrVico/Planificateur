package fr.univtln.maxremvi.Database;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.univtln.maxremvi.Controller.PersonController;
import fr.univtln.maxremvi.Model.*;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseMigrator {
    private static void createTables() throws SQLException, IOException {
        ConnectionSource connectionSource = DatabaseUtil.getConnectionSource();

        TableUtils.createTable(connectionSource, Person.class);
        TableUtils.createTable(connectionSource, Poll.class);
        TableUtils.createTable(connectionSource, Invitation.class);
        TableUtils.createTable(connectionSource, AnswerChoice.class);
        TableUtils.createTable(connectionSource, Answer.class);
        TableUtils.createTable(connectionSource, Message.class);

        DatabaseUtil.closeConnection();
    }

    private static void addPerson() {
        PersonController personController = new PersonController();
        try {
            personController.addPerson("dzdz", "mguil", "max", "guil", "dzd@guil");
            personController.addPerson("dzd", "mguil", "max", "guil", "dddzd@guil");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addPerson();
    }
}
