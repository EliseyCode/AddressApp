package ru.enovikow.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.enovikow.address.model.Person;
import ru.enovikow.address.util.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RootLayoutController extends UIFormController implements Initializable {

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
    @FXML
    private Button exitButton;

    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private final static String ADD_CONTACT_RESOURCE = "/UIForms/AddContact.fxml";

    private Stage primaryStage;
    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getDatabaseHandler();
        loadData();
    }

    private void loadData() {
//        DatabaseHandler handler = new DatabaseHandler();
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

            personTableView.setItems(personData);
            IDColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty().asObject());
            firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
            surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
            birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdayProperty());
            addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        } catch (SQLException e) {
        }
    }

    public RootLayoutController() {
    }

    public void openAddContact(ActionEvent actionEvent) {
        showPersonEditDialog();
    }

    public boolean showPersonEditDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource(ADD_CONTACT_RESOURCE));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(this);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            personData.remove(0, personData.size());
            loadData();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected String formSource() {
        return null;
    }

    @Override
    protected void setStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void exitButtonAction() {
        System.exit(0);
    }
}