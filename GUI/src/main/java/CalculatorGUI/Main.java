package CalculatorGUI;

import expressionEvaluation.DerivationEngine;
import expressionEvaluation.Function;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/chart.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        expressionEvaluation.Function f = new expressionEvaluation.Function("log10(x)");

        System.out.println(f.rpnToString());

        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i + " value = " + f.derivative.evaluate(i));
        }
    }


}

//public class CalculatorGUI.Main {
//    public static void main(String[] args) {
//        expressionEvaluation.Function f = new expressionEvaluation.Function("(x^3-8)/(x-2)");
//
//        System.out.println(f.rpnToString());
//
//        for (int i = 0; i < 10; i++){
//            System.out.println("i = " + i + " value = " + f.secondDerivative.evaluate(i));
//        }
//    }
//}
