package com.example.medis;

import javafx.application.Application;
import javafx.beans.value.ObservableLongValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        Locale locale = new Locale("EN");
        FXMLLoader loader = new FXMLLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("medis", locale);
        loader.setLocation(Objects.requireNonNull(getClass().getResource("login.fxml")));
        loader.setResources(bundle);
        Parent root = loader.load();
        primaryStage.setTitle("Medis");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}