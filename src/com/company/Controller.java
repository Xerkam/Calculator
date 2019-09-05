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
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField inputTextField;

    @FXML
    private LineChart graph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graph.setAnimated(false);
    }

//    @FXML
////    Graphs parent function
//    private void handleGraphButtonAction(final ActionEvent event) {
//        if (!inputTextField.getText().isBlank()){
//
//            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
//            Function func = new Function(inputTextField.getText());
//
//            for (double i = -5; i < 5; i += .05)
//                series.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));
//
//            graph.setCreateSymbols(false);
//            series.setName(func.toString());
//            graph.getData().add(series);
//        }
//    }

    @FXML
//    Graphs parent function
    private void handleGraphButtonAction(final ActionEvent event) {
        graph.getData().clear();
        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<>();
            Function func = new Function(inputTextField.getText());
            XYChart.Series<Double, Double> zeros = new XYChart.Series<>();

            for (double i = -5; i < 5; i += .01) {
                if(!(func.bisectionMethod(i, i + .01) == null))
                    zeros.getData().add(new XYChart.Data<Double, Double>(i, 0.0));
                series.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));
            }

            series.setName(func.toString());
            graph.getData().add(series);
            graph.getData().add(zeros);
            System.out.println(Arrays.toString(zeros.getData().toArray()));
//            graph.getData().remove(zeros);

        }
    }

    @FXML
//    Graphs First Derivative
    private void handleDerivative1ButtonAction(final ActionEvent event) {
        graph.getData().clear();

        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            Function func = new Function(inputTextField.getText());
            XYChart.Series<Double, Double> zeros = new XYChart.Series<>();


            for (double i = -5; i < 5; i += .01) {
                if (!(func.bisectionMethodDerivative(i, i + .01) == null))
                    zeros.getData().add(new XYChart.Data<Double, Double>(i, 0.0));
                series.getData().add(new XYChart.Data<Double, Double>(i, func.derivative(i)));
            }

            series.setName("dx(" + func.toString() + ")");
            graph.getData().add(series);
            graph.getData().add(zeros);

        }
    }

    @FXML
//    Graphs Second Derivative
    private void handleDerivative2ButtonAction(final ActionEvent event) {
        graph.getData().clear();

        if (!inputTextField.getText().isBlank()){

            XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            Function func = new Function(inputTextField.getText());
            XYChart.Series<Double, Double> zeros = new XYChart.Series<>();


            for (double i = -5; i < 5; i += .01) {
                if (!(func.bisectionMethod(i, i + .01) == null))
                    zeros.getData().add(new XYChart.Data<Double, Double>(i, 0.0));
                series.getData().add(new XYChart.Data<Double, Double>(i, func.secondDerivative(i)));
            }

            series.setName("d2x(" + func.toString() + ")");
            graph.getData().add(series);
            graph.getData().add(zeros);
        }
    }

    @FXML
//    Clears Graph
    private void handleClearButtonAction(final ActionEvent event){
        graph.getData().clear();
    }

}
