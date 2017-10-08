package ru.enovikow.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.enovikow.address.model.Person;
import ru.enovikow.address.util.DatabaseHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public abstract class UIFormController {

    protected abstract String formSource();

    protected abstract void setStage(Stage stage);

    private static ObservableList<Person> personData = FXCollections.observableArrayList();
    private DatabaseHandler databaseHandler;

    {
        databaseHandler = DatabaseHandler.getDatabaseHandler();
    }

    protected void loadWindow() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(formSource()));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            setStage(stage);
            stage.show();
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }

    protected void loadData() {
        personData.remove(0, personData.size());
        String query = "SELECT * FROM contacts";
        ResultSet rs = databaseHandler.execQuery(query);
        try {
            while (rs.next()) {
                Integer ID = rs.getInt("ID");
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();
                String address = rs.getString("ADDRESS");

                personData.add(new Person(ID, name, surname, address, birthday));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Person> getPersonData() {
        return personData;
    }

    public static void setPersonData(ObservableList<Person> data) {
        personData = data;
    }
}