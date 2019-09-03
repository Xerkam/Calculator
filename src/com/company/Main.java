package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader loader = new FXMLLoader(new File("/Users/danielpeshev/Documents/GitHub/Calculator/resources/sample.fxml").toURI().toURL());
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 666, 550));

        primaryStage.setX(666);
        primaryStage.setY(550);

        primaryStage.show();
        System.out.println(new Function("x^2").derivative(.05));

    }


}
