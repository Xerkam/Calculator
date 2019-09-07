package com.company;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField inputTextField;

    @FXML
    private LineChart graph;

    private double boundA, boundB, previousX, currentX, nextX;

    private Function func;

    private XYChart.Series<Double, Double> series, extrema, inflection;

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


//    private void handleGraphButtonAction(final ActionEvent event) {
////        String textfield = inputTextField.getText();
////        graph.getData().clear();
////        if (!textfield.isBlank()) {
////
////            ArrayList<Double> foundExtrema = new ArrayList<>();
////            int boundB = 10;
////            int boundA = -10;
////            Function func = new Function(textfield);
////            XYChart.Series<Double, Double> series = new XYChart.Series<>();
////            XYChart.Series<Double, Double> extrema = new XYChart.Series<>();
////
////            //Adds points on graph
////            for (double i = boundA; i < boundB; i += .01)
////                series.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));
////
////            //Tests for extrema, if a potential zero is found, verify whether zero is already graphed and if zero is within
////            //bounds of graphed graph
////            for (double i =  boundA; i < boundB; i += .1) {
////                Double potentialValue = func.newtonsMethodDerivative(i);
////                if (!(potentialValue == null)
////                        && !ArrayTraverse.contains(ArrayTraverse.primitive(foundExtrema), potentialValue)
////                        && (potentialValue < boundB && potentialValue > boundA)) {
////
////                    extrema.getData().add(new XYChart.Data<Double, Double>(potentialValue, func.evaluate(potentialValue)));
////                    foundExtrema.add(potentialValue);
////                }
////            }
////
////
////            series.setName(func.toString());
////            graph.getData().add(series);
////            graph.getData().add(extrema);
//////            graph.getData().remove(extrema);
////        }
////    }

    @FXML
//    Graphs parent function

    private void handleGraphButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String textfield = inputTextField.getText();

        if (!textfield.isBlank()) {

            boundB = 10;
            boundA = .01;
            func = new Function(textfield);
            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();

            //Adds points on graph
            for (double i = boundA + .01; i < boundB; i += .01) {
                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                checks to see if each plotted point is an extrema.

                if((func.evaluate(currentX) > func.evaluate(previousX) && func.evaluate(currentX) > func.evaluate(nextX))
                || (func.evaluate(currentX) < func.evaluate(previousX) && func.evaluate(currentX) < func.evaluate(nextX)))
                    extrema.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));

//                checks to see if each plotted point is a POI via first derivative extrema

                if((func.derivative(currentX) > func.derivative(previousX) && func.derivative(currentX) > func.derivative(nextX))
                || (func.derivative(currentX) < func.derivative(previousX) && func.derivative(currentX) < func.derivative(nextX))) {
                    System.out.println(previousX + ',' + func.derivative(previousX) + " " + currentX + ',' + func.derivative(currentX) + " " + nextX + ',' + func.derivative(nextX));
                    inflection.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));
                }

//                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)

                if(!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<Double, Double>(i, func.evaluate(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

        }
    }



    @FXML
//    Graphs First Derivative
    private void handleDerivative1ButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String textfield = inputTextField.getText();

        if (!textfield.isBlank()) {

            func = new Function(textfield);
            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();

            //Adds points on graph
            for (double i = boundA + .01; i < boundB -.01; i += .01) {
                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                checks to see if each plotted point is an extrema.

                if((func.derivative(currentX) > func.derivative(previousX) && func.derivative(currentX) > func.derivative(nextX))
                        || (func.derivative(currentX) < func.derivative(previousX) && func.derivative(currentX) < func.derivative(nextX)))
                    extrema.getData().add(new XYChart.Data<Double, Double>(i, func.derivative(i)));

//                checks to see if each plotted point is a POI via following derivative extrema

                if((func.secondDerivative(currentX) > func.secondDerivative(previousX) && func.secondDerivative(currentX) > func.secondDerivative(nextX))
                        || (func.secondDerivative(currentX) < func.secondDerivative(previousX) && func.secondDerivative(currentX) < func.secondDerivative(nextX)))
                    inflection.getData().add(new XYChart.Data<Double, Double>(i, func.derivative(i)));

//                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)

                if(!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<Double, Double>(i, func.derivative(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

        }
    }

    @FXML
//    Graphs Second Derivative
    private void handleDerivative2ButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String textfield = inputTextField.getText();

        if (!textfield.isBlank()) {

            func = new Function(textfield);
            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();

            //Adds points on graph
            for (double i = boundA + .01; i < boundB -.01; i += .01) {
                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                checks to see if each plotted point is an extrema.

                if((func.secondDerivative(currentX) > func.secondDerivative(previousX) && func.secondDerivative(currentX) > func.secondDerivative(nextX))
                        || (func.secondDerivative(currentX) < func.secondDerivative(previousX) && func.secondDerivative(currentX) < func.secondDerivative(nextX)))
                    extrema.getData().add(new XYChart.Data<Double, Double>(i, func.secondDerivative(i)));

//                checks to see if each plotted point is a POI via following derivative extrema

                if((func.thirdDerivative(currentX) > func.thirdDerivative(previousX) && func.thirdDerivative(currentX) > func.thirdDerivative(nextX))
                        || (func.thirdDerivative(currentX) < func.thirdDerivative(previousX) && func.thirdDerivative(currentX) < func.thirdDerivative(nextX)))
                    inflection.getData().add(new XYChart.Data<Double, Double>(i, func.secondDerivative(i)));

//                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)

                if(!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<Double, Double>(i, func.secondDerivative(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

        }
    }

    @FXML
//    Clears Graph
    private void handleClearButtonAction(final ActionEvent event){
        graph.getData().clear();
    }

}
