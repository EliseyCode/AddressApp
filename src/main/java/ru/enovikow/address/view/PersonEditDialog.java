package ru.enovikow.address.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.enovikow.address.util.DatabaseHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonEditDialog implements Initializable{

    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField dayTextField;
    @FXML
    private TextField monthTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField addressTextField;

    private DatabaseHandler databaseHandler;

    private Stage dialogStage;
    private boolean okClicked;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseHandler = DatabaseHandler.getDatabaseHandler();
    }

    public void okButtonAction(ActionEvent actionEvent) {
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String day = dayTextField.getText();
        String month = monthTextField.getText();
        String year = yearTextField.getText();
        String address = addressTextField.getText();
        if(databaseHandler.addContactToDb(firstname, lastname, day, month, year, address)) {
            dialogStage.close();
        }

    }

    public void cancelButtonAction(ActionEvent actionEvent) {
//        ((Stage)firstnameTextField.getScene().getWindow()).close();
        dialogStage.close();

    }
}