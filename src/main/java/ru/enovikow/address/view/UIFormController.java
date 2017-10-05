package ru.enovikow.address.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class UIFormController {

    protected abstract String formSource();
    protected abstract void setStage(Stage stage);

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
}
