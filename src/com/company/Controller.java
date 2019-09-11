package com.company;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField functionField;

    @FXML
    private LineChart graph;

    @FXML
    private TextField lowerBoundField;

    @FXML
    private TextField upperBoundField;

    private double boundA, boundB;

    private double previousX, currentX, nextX;

    private double tolerance = Math.pow(10, -7);

    private Function func;

    private XYChart.Series<Double, Double> series, extrema, inflection, discontinuities;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graph.setAnimated(false);
    }

//    @FXML
////    Graphs parent function
//    private void handleGraphButtonAction(final ActionEvent event) {
//        if (!inputTextField.getText().isBlank()){
//
//            XYChart.Series<> series = new XYChart.Series<>();
//            Function func = new Function(inputTextField.getText());
//
//            for (double i = -5; i < 5; i += .05)
//                series.getData().add(new XYChart.Data<>(i, func.evaluate(i)));
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
////            XYChart.Series<> series = new XYChart.Series<>();
////            XYChart.Series<> extrema = new XYChart.Series<>();
////
////            //Adds points on graph
////            for (double i = boundA; i < boundB; i += .01)
////                series.getData().add(new XYChart.Data<>(i, func.evaluate(i)));
////
////            //Tests for extrema, if a potential zero is found, verify whether zero is already graphed and if zero is within
////            //bounds of graphed graph
////            for (double i =  boundA; i < boundB; i += .1) {
////                Double potentialValue = func.newtonsMethodDerivative(i);
////                if (!(potentialValue == null)
////                        && !ArrayTraverse.contains(ArrayTraverse.primitive(foundExtrema), potentialValue)
////                        && (potentialValue < boundB && potentialValue > boundA)) {
////
////                    extrema.getData().add(new XYChart.Data<>(potentialValue, func.evaluate(potentialValue)));
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
    @SuppressWarnings("Duplicates")//Suppress duplicate warning given by intializing the variables in F(x)/F'(x)/F''(x)
//    Graphs parent function

    private void handleGraphButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String funcField = functionField.getText();
        String boundAField = lowerBoundField.getText();
        String boundBField = upperBoundField.getText();

        if (!boundAField.isBlank())
            boundA = Double.parseDouble(boundAField);
        else
            boundA = -10;

        if (!boundBField.isBlank())
            boundB = Double.parseDouble(boundBField);
        else
            boundB = 10;

        if (!funcField.isBlank()) {

            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();
            func = new Function(funcField);

            //Adds points on graph
            for (double i = boundA + .01; i < boundB; i += .01) {

                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                checks to see if each plotted point is an extrema.
//
                if (func.evaluate(currentX) > func.evaluate(previousX) && func.evaluate(currentX) > func.evaluate(nextX)
                        || func.evaluate(currentX) < func.evaluate(previousX) && func.evaluate(currentX) < func.evaluate(nextX)) {
                    Double bisectionZero = func.bisectionMethodDerivative(previousX, nextX);
                    if (bisectionZero != null) {
                        extrema.getData().add(new XYChart.Data<>(i, func.evaluate(i)));
                    }
                }
//                Alternate implementation below.

//                if ((func.evaluate(currentX) - func.evaluate(previousX) > tolerance
//                        && func.evaluate(currentX) - func.evaluate(nextX) > tolerance)
//                        || (func.evaluate(currentX) - func.evaluate(previousX) < -tolerance
//                        && func.evaluate(currentX) - func.evaluate(nextX) < -tolerance))
//                    extrema.getData().add(new XYChart.Data<>(i, func.evaluate(i)));

//                checks to see if each plotted point is a POI via first derivative extrema; identifies if currentX is either
//                greater than prev/nextX or less than prev/nextX. Then uses the bisection method from prevX to nextX to
//                more accurately identify where a POI could occur.

                if (func.derivative(currentX) > func.derivative(previousX) && func.derivative(currentX) > func.derivative(nextX)
                        || func.derivative(currentX) < func.derivative(previousX) && func.derivative(currentX) < func.derivative(nextX)) {
//                    System.out.println(" previousX " + previousX + "," + func.derivative(previousX) + " currentX " + currentX + "," + func.evaluate(currentX) + " nextX " + nextX + "," + func.derivative(nextX));
                    Double bisectionZero = func.bisectionMethodSecondDerivative(previousX, nextX);
                    if (bisectionZero != null) {
                        inflection.getData().add(new XYChart.Data<>(bisectionZero, func.evaluate(bisectionZero)));
                    }
                }

//                Alternate implementation below

//                if ((func.derivative(currentX) - func.derivative(previousX) > tolerance
//                        && func.derivative(currentX) - func.derivative(nextX) > tolerance)
//                        || (func.derivative(currentX) - func.derivative(previousX) < -tolerance
//                        && func.derivative(currentX) - func.derivative(nextX) < -tolerance))
//                    inflection.getData().add(new XYChart.Data<>(i, func.evaluate(i)));

//                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)

                if (!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<>(i, func.evaluate(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

            //If function has denominator, check to see if removable discontinuities exist.

            if (funcField.contains("/")) {
                String denominator = funcField.substring(funcField.indexOf("/") + 1);
                discontinuities = new XYChart.Series<>();

                Function temp = new Function(denominator);

                for (double i = boundA + .01; i < boundB; i += .01) {
                    previousX = i - .01;
                    currentX = i;
                    nextX = i + .01;

                    if ((temp.evaluate(previousX) > 0 && temp.evaluate(nextX) < 0)
                            || (temp.evaluate(previousX) < 0 && temp.evaluate(nextX) > 0)) {
                        double zero = temp.newtonsMethod(currentX);
                        if (func.isRemovableDiscontinuity(zero))
                            discontinuities.getData().add(new XYChart.Data<>(zero, func.evaluate(zero + Math.pow(10, -7))));
                    }
                }
                discontinuities.setName("discontinuities");
                graph.getData().add(discontinuities);
            }


        }
    }

    @FXML
    @SuppressWarnings("Duplicates")//Suppress duplicate warning given by intializing the variables in F(x)/F'(x)/F''(x)
//    Graphs First Derivative

    private void handleDerivative1ButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String funcField = functionField.getText();
        String boundAField = lowerBoundField.getText();
        String boundBField = upperBoundField.getText();

        if (!boundAField.isBlank())
            boundA = Double.parseDouble(boundAField);
        else
            boundA = -10;

        if (!boundBField.isBlank())
            boundB = Double.parseDouble(boundBField);
        else
            boundB = 10;

        if (!funcField.isBlank()) {

            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();
            func = new Function(funcField);

            //Adds points on graph
            for (double i = boundA + .01; i < boundB; i += .01) {

                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                Computes extrema/POI of first derivative, too much floating point inaccuracy to function across all
//                equations.

////                checks to see if each plotted point is an extrema.
////
//                if ((func.derivative(currentX) - func.derivative(previousX) > tolerance
//                        && func.derivative(currentX) - func.derivative(nextX) > tolerance)
//                        || (func.derivative(currentX) - func.derivative(previousX) < -tolerance
//                        && func.derivative(currentX) - func.derivative(nextX) < -tolerance))
//                    extrema.getData().add(new XYChart.Data<>(i, func.derivative(i)));
//
//
////                checks to see if each plotted point is a POI via following derivative extrema
//
//                if ((func.secondDerivative(currentX) - func.secondDerivative(previousX) > tolerance
//                        && func.secondDerivative(currentX) - func.secondDerivative(nextX) > tolerance)
//                        || (func.secondDerivative(currentX) - func.secondDerivative(previousX) < -tolerance
//                        && func.secondDerivative(currentX) - func.secondDerivative(nextX) < -tolerance))
//                    inflection.getData().add(new XYChart.Data<>(i, func.derivative(i)));
//
////                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)
//
                if (!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<>(i, func.derivative(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

            //If function has denominator, check to see if removable discontinuities exist.

            if (funcField.contains("/")) {
                String denominator = funcField.substring(funcField.indexOf("/") + 1);
                discontinuities = new XYChart.Series<>();

                Function temp = new Function(denominator);

                for (double i = boundA + .01; i < boundB; i += .01) {
                    previousX = i - .01;
                    currentX = i;
                    nextX = i + .01;

                    if ((temp.evaluate(previousX) > 0 && temp.evaluate(nextX) < 0)
                            || (temp.evaluate(previousX) < 0 && temp.evaluate(nextX) > 0)) {
                        double zero = temp.newtonsMethod(currentX);
                        if (func.isRemovableDiscontinuity(zero))
                            discontinuities.getData().add(new XYChart.Data<>(zero, func.derivative(zero + Math.pow(10, -7))));
                    }
                }
                discontinuities.setName("discontinuities");
                graph.getData().add(discontinuities);
            }


        }
    }

    @FXML
    @SuppressWarnings("Duplicates")//Suppress duplicate warning given by intializing the variables in F(x)/F'(x)/F''(x)
//    Graphs Second Derivative

    private void handleDerivative2ButtonAction(final ActionEvent event) {

        graph.getData().clear();
        String funcField = functionField.getText();
        String boundAField = lowerBoundField.getText();
        String boundBField = upperBoundField.getText();

        if (!boundAField.isBlank())
            boundA = Double.parseDouble(boundAField);
        else
            boundA = -10;

        if (!boundBField.isBlank())
            boundB = Double.parseDouble(boundBField);
        else
            boundB = 10;

        if (!funcField.isBlank()) {

            series = new XYChart.Series<>();
            extrema = new XYChart.Series<>();
            inflection = new XYChart.Series<>();
            func = new Function(funcField);

            //Adds points on graph
            for (double i = boundA + .01; i < boundB; i += .01) {

                previousX = i - .01;
                currentX = i;
                nextX = i + .01;

//                Computes extrema/POI of second derivative, too much floating point inaccuracy to function across all
//                equations.

////                checks to see if each plotted point is an extrema.
//
//                if (((func.secondDerivative(currentX) - func.secondDerivative(previousX)) > tolerance
//                        && (func.secondDerivative(currentX) - func.secondDerivative(nextX)) > tolerance)
//                        || ((func.secondDerivative(currentX) - func.secondDerivative(previousX)) < -tolerance
//                        && (func.secondDerivative(currentX) - func.secondDerivative(nextX)) < -tolerance))
//                    extrema.getData().add(new XYChart.Data<>(i, func.secondDerivative(i)));
//
////                checks to see if each plotted point is a POI via following derivative extrema
//
//                if ((func.thirdDerivative(currentX) - func.thirdDerivative(previousX) > tolerance
//                        && func.thirdDerivative(currentX) - func.thirdDerivative(nextX) > tolerance)
//                        || (func.thirdDerivative(currentX) - func.thirdDerivative(previousX) < -tolerance
//                        && func.thirdDerivative(currentX) - func.thirdDerivative(nextX) < -tolerance))

//                if (func.thirdDerivativeGraph(currentX) > func.thirdDerivativeGraph(previousX) && func.thirdDerivativeGraph(currentX) > func.thirdDerivativeGraph(nextX)
//                        || func.thirdDerivativeGraph(currentX) < func.thirdDerivativeGraph(previousX) && func.thirdDerivativeGraph(currentX) < func.thirdDerivativeGraph(nextX))
//                    inflection.getData().add(new XYChart.Data<>(i, func.secondDerivative(i)));

////                charts points of graph (NaN required to graph functions with naturally bounded areas ex: log10x, ln(x)

                if (!Double.isNaN(func.evaluate(i)))
                    series.getData().add(new XYChart.Data<>(i, func.secondDerivative(i)));

            }

            series.setName(func.toString());
            extrema.setName("extrema");
            inflection.setName("inflection points");
            graph.getData().add(series);
            graph.getData().add(extrema);
            graph.getData().add(inflection);

            if (funcField.contains("/")) {
                String denominator = funcField.substring(funcField.indexOf("/") + 1);
                discontinuities = new XYChart.Series<>();

                Function temp = new Function(denominator);

                for (double i = boundA + .01; i < boundB; i += .01) {
                    previousX = i - .01;
                    currentX = i;
                    nextX = i + .01;

                    if ((temp.evaluate(previousX) > 0 && temp.evaluate(nextX) < 0)
                            || (temp.evaluate(previousX) < 0 && temp.evaluate(nextX) > 0)) {
                        double zero = temp.newtonsMethod(currentX);
                        if (func.isRemovableDiscontinuity(zero))
                            discontinuities.getData().add(new XYChart.Data<>(zero, func.secondDerivative(zero + Math.pow(10, -7))));
                    }
                }
                discontinuities.setName("discontinuities");
                graph.getData().add(discontinuities);
            }


        }
    }

    @FXML
//    Clears Graph
    private void handleClearButtonAction(final ActionEvent event) {
        graph.getData().clear();
    }

}
