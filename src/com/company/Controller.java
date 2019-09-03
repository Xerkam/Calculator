package com.company;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;


import javax.swing.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField inputTextField;

    @FXML
    private LineChart graph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
//    Graphs parent function
    private void handleGraphButtonAction(final ActionEvent event) {
        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            Function func = new Function(inputTextField.getText());

            for (double i = -5; i < 5; i += .05)
                series.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));

            graph.setCreateSymbols(false);
            series.setName(func.toString());
            graph.getData().add(series);
        }
    }

    @FXML
//    Graphs First Derivative
    private void handleDerivative1ButtonAction(final ActionEvent event) {
        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            Function func = new Function(inputTextField.getText());

            for (double i = -5; i < 5; i += .1)
                series.getData().add(new XYChart.Data<Double, Double>(i, func.derivative(i)));

            graph.setCreateSymbols(false);
            series.setName(func.toString());
            graph.getData().add(series);
        }
    }

    @FXML
//    Graphs Second Derivative
    private void handleDerivative2ButtonAction(final ActionEvent event) {
        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            Function func = new Function(inputTextField.getText());

            for (double i = -5; i < 5; i += .05)
                series.getData().add(new XYChart.Data<Double, Double>(i, func.secondDerivative(i)));

            graph.setCreateSymbols(false);
            series.setName(func.toString());
            graph.getData().add(series);
        }
    }

    @FXML
//    Clears Graph
    private void handleClearButtonAction(final ActionEvent event){
        graph.getData().clear();
    }

}
