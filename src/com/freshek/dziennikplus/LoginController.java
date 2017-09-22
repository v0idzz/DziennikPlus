package com.freshek.dziennikplus;

import com.freshek.dziennikplus.diary.api.UonetPlusAPI;
import com.freshek.dziennikplus.preferences.Settings;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Freshek on 19.09.17.
 *
 * a class to login in
 */
public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private TextField idField;

    /**
     * Validates the credentials
     * and runs the Main class
     */
    @FXML
    private void login() {
        if (vaildField(emailField) && vaildField(passField) && vaildField(idField)) {
            init(emailField.getText(), passField.getText(), idField.getText());
        }

    }

    void init(String username, String password, String clientId) {
        UonetPlusAPI api = new UonetPlusAPI(username, password, clientId);
        if (api.login()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loader.load();

                Stage primaryStage = new Stage();
                primaryStage.setTitle("Dziennik+ – beta");
                Scene scene = new Scene(root, 1300, 900);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();

                Controller controller = loader.getController();
                controller.init(api);

                Settings settings = new Settings(username, password, clientId);
                settings.save();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nie można zalogować");
                alert.setHeaderText("Sprawdź hasło oraz e-mail");
                alert.setContentText(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the field is really 'empty'
     * @param field a field to check
     * @return result of the check
     */
    private boolean vaildField(TextField field) {
        if (field == null)
            throw new NullPointerException();
        return field.getText().length() > 0;
    }
}
