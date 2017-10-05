package ru.enovikow.address.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.enovikow.address.util.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUPController extends UIFormController implements Initializable {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUPButton;
    @FXML
    private Button loginButton;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;

    private static final String LOGIN_FORM_ADDRESS = "/UIForms/Login.fxml";
    private static final String LOGIN_TITLE_MESSAGE = "";

    private DatabaseHandler databaseHandler;
    private Stage signUPStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected String formSource() {
        return LOGIN_FORM_ADDRESS;
    }

    @Override
    protected void setStage(Stage stage) {
        this.signUPStage = stage;
    }

    public void loginAction(ActionEvent actionEvent) {
        ((Stage) loginField.getScene().getWindow()).close();
        loadWindow();
    }

    public void signUPAction(ActionEvent actionEvent) {
        String username = loginField.getText();
        String password = passwordField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (username.length() < 4 && password.length() < 4) {
            System.out.println("If block enter");
            alert.setTitle("");
            alert.showAndWait();
        } else {
            databaseHandler = DatabaseHandler.getDatabaseHandler();
            if (!databaseHandler.createCustomer(username, password)) {
                alert.setTitle("Registration Error");
                alert.setHeaderText("Login already exist in DB");
                alert.setContentText("Please try another login");
                alert.showAndWait();
            } else {
                alert.setTitle("Registration Success");
                alert.setHeaderText("Account " + username + " successfully created");
                alert.showAndWait();
            }
        }
    }

    public void femaleClickedAction(MouseEvent mouseEvent) {
        if (maleRadioButton.isDisabled()) {
            maleRadioButton.setDisable(false);
        } else {
            maleRadioButton.setDisable(true);
        }
    }

    public void maleClickedAction(MouseEvent mouseEvent) {
        if (femaleRadioButton.isDisabled()) {
            femaleRadioButton.setDisable(false);
        } else {
            femaleRadioButton.setDisable(true);
        }
    }
}