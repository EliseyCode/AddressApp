package ru.enovikow.address.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.enovikow.address.model.Person;
import ru.enovikow.address.util.DatabaseHandler;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditContactController extends UIFormController implements Initializable {

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, Integer> IDColumn;
    @FXML
    private TableColumn<Person, String> firstnameColumn;
    @FXML
    private TableColumn<Person, String> surnameColumn;
    @FXML
    private TableColumn<Person, String> addressColumn;
    @FXML
    private TableColumn<Person, LocalDate> birthdayColumn;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField birthdayDayTextField;
    @FXML
    private TextField birthdayMonthTextField;
    @FXML
    private TextField birthdayYearTextField;
    @FXML
    private TextField addressTextField;

    private ObservableList<Person> personData = RootLayoutController.personData;
    private DatabaseHandler databaseHandler;
    private Stage dialogStage;
    private int id;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getDatabaseHandler();

        loadData();

        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void showPersonDetails(Person newValue) {
        if (newValue != null) {
            // Заполняем метки информацией из объекта person.
            id = newValue.getID();
            firstnameTextField.setText(newValue.getFirstName());
            surnameTextField.setText(newValue.getLastName());
            birthdayDayTextField.setText(String.valueOf(newValue.getBirthday().getDayOfMonth()));
            birthdayMonthTextField.setText(String.valueOf(newValue.getBirthday().getMonthValue()));
            birthdayYearTextField.setText(String.valueOf(newValue.getBirthday().getYear()));
            addressTextField.setText(newValue.getAddress());
        } else {
            // Если Person = null, то убираем весь текст.
            firstnameTextField.setText("");
            surnameTextField.setText("");
            addressTextField.setText("");
        }
    }

    @FXML
    public void updateButtonAction() {
        String birthday = birthdayYearTextField.getText() + "-" + birthdayMonthTextField.getText() + "-" + birthdayDayTextField.getText();
        databaseHandler.updateContact(id, firstnameTextField.getText(), surnameTextField.getText(), birthday, addressTextField.getText());
        loadData();
    }

    @Override
    protected String formSource() {
        return null;
    }

    @Override
    protected void setStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void okButtonAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void cancelButtonAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void searchButtonAction(ActionEvent actionEvent) {

    }

    @FXML
    public void deleteButtonAction(ActionEvent actionEvent) {
        databaseHandler.deleteContact(id);
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
}