package ru.enovikow.address.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import ru.enovikow.address.util.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends UIFormController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUPButton;

    private static String SIGN_UP_FORM_RESOURCE = "/UIForms/SignUP.fxml";

    private Stage loginStage;
    private DatabaseHandler databaseHandler;

    public void signUPAction(ActionEvent actionEvent) {
        ((Stage) usernameField.getScene().getWindow()).close();
        loadWindow();
    }

    @Override
    protected void setStage(Stage stage) {
        loginStage = stage;
    }

    public void loginAction(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (databaseHandler.isAccountExisting(username, password)) {
            SIGN_UP_FORM_RESOURCE = "/UIForms/RootLayout.fxml";
            ((Stage) usernameField.getScene().getWindow()).close();
            loadWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication Error");
            alert.setHeaderText("Wrong Username or Password");
            alert.setContentText("Please try again or register new customer account");
            alert.showAndWait();
        }
    }

    @Override
    protected String formSource() {
        return SIGN_UP_FORM_RESOURCE;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getDatabaseHandler();
    }
}