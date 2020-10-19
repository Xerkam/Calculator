import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }


}

//public class Main {
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
