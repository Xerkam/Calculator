package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;


//*******************To Do*******************
//Implement bounds (ln(x) and log10(x) show up as having POI at end point due to improperly set bounds
//Fundemental Theorem of Calc


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        FXMLLoader loader = new FXMLLoader(new File("/Users/danielpeshev/Documents/GitHub/Calculator/resources/sample.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 666, 550);
        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setX(666);
        primaryStage.setY(550);

        System.out.println(new Function("ln(x)").evaluate(-.01) + 1);

        primaryStage.show();








    }


}
