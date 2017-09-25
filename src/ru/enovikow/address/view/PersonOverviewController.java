package ru.enovikow.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.enovikow.address.MainApp;
import ru.enovikow.address.model.Person;
import ru.enovikow.address.util.DateUtil;

public class PersonOverviewController {

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    private MainApp mainApp;

    /**
     *
     */
    public PersonOverviewController() {

    }

    /**
     *
     * @param person
     */
    private void showPersonDetails(Person person) {
       if(person != null) {
           // Заполняем метки информацией из объекта person
           firstNameLabel.setText(person.getFirstName());
           lastNameLabel.setText(person.getLastName());
           streetLabel.setText(person.getStreet());
           postalLabel.setText(Integer.toString(Integer.parseInt(person.getPostalCode())));
           cityLabel.setText(person.getCity());
           birthdayLabel.setText(DateUtil.format(person.getBirthday()));
       } else {
           // Если person = null убираем весь текст
           firstNameLabel.setText("");
           lastNameLabel.setText("");
           streetLabel.setText("");
           postalLabel.setText("");
           cityLabel.setText("");
           birthdayLabel.setText("");
       }
    }

    /**
     * Инициализация класса-контрроллера. Метод вызывается автоматически после того как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        // Очистка дополнительной информации об адресате.
        showPersonDetails(null);

        // Слушаем изменения выбора и при изменении отображаем доп. информацию об адресате.
        personTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Вызывется главным приложением, которое дает на себя ссылку.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
        personTableView.setItems(mainApp.getPersonData());
    }

    /**
     * Вызывается когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            personTableView.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ошибка выбора");
            alert.setHeaderText("Контакт не выбран");
            alert.setContentText("Для удаления контакта из базы выберете пользователя из списка");

            alert.showAndWait();
        }
    }
}
