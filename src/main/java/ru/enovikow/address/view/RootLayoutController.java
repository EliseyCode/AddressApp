package ru.enovikow.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.enovikow.address.model.Person;
import ru.enovikow.address.util.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    @FXML
    private Button addContact;

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> firstnameColumn;
    @FXML
    private TableColumn<Person, String> surnameColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, Integer> IDColumn;
    @FXML
    private TableColumn<Person, LocalDate> birthdayColumn;



    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadData();
        personTableView.setItems(personData);
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty().asObject());
        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdayProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());


    }

    private void loadData() {
        DatabaseHandler handler = new DatabaseHandler();
        String query = "SELECT * FROM contacts";
        ResultSet rs = handler.execQuery(query);
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
            System.out.println("Shit in loadData");
        }
    }

    public RootLayoutController() {
    }

    public void openAddContact(ActionEvent actionEvent) {
        System.out.println("open");
    }
}
