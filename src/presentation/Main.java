package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Parent p = (Parent) FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        Scene s = new Scene(p);

        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
