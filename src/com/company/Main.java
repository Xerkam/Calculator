package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Stack;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//        Region sidebar = new Region();
//
//        HBox hbox = new HBox(sidebar);
//
//        sidebar.getStyleClass().add("sidebar");
//
//        sidebar.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//
//        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
//
//        XYChart.Series series = new XYChart.Series();
//
//        Scene scene  = new Scene(lineChart,800,600);
//        lineChart.getData().add(series);
//
//        primaryStage.setScene(scene);

        primaryStage.show();

    }


}
