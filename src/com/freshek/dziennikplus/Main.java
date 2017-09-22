package com.freshek.dziennikplus;

import com.freshek.dziennikplus.preferences.Settings;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Settings settings = null;
        try {
            if (Files.exists(Paths.get("settings.cfg"))) {
                Scanner in = new Scanner(new FileReader("settings.cfg"));
                StringBuilder sb = new StringBuilder();
                while (in.hasNext())
                    sb.append(in.nextLine());

                settings = new Gson().fromJson(sb.toString(), Settings.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Dziennik+ – beta");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 450, 180));

        if (settings != null)
            ((LoginController)loader.getController()).init(settings.getEmail(), settings.getPassword(), settings.getClientId());
        else
            primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
