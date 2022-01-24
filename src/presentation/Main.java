package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    private static final float MIN_WIDTH = 300;
    @Override
    public void start(Stage stage) throws Exception {

        Parent p = (Parent) FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        Scene s = new Scene(p);

        stage.setScene(s);
        stage.setMinWidth(MIN_WIDTH);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
