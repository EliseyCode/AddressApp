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

    public static ObservableList<Person> personData;// = UIFormController.getPersonData();
    private final static String ADD_CONTACT_RESOURCE = "/UIForms/AddContact.fxml";

    private Stage primaryStage;
    private DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getDatabaseHandler();
        loadData();
    }

    @Override
    protected void loadData() {
        super.loadData();
        personData = UIFormController.getPersonData();
        personTableView.setItems(personData);
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getIDProperty().asObject());
        firstnameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdayProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
    }

    public RootLayoutController() {
    }

    public void openAddContact(ActionEvent actionEvent) {
        showNewContactDialog();
    }

    public void editContactsButtonAction() {
        showEditContactDialog();
    }

    private void showEditContactDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("/UIForms/EditContactDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditContactController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setPersonData(personData);
            dialogStage.showAndWait();
            loadData();
        } catch (IOException e) {

        }
    }

    public boolean showNewContactDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource(ADD_CONTACT_RESOURCE));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(this);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddContactController controller = loader.getController();
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