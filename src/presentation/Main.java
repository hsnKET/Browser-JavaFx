package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    private static final float MIN_WIDTH = 350;
    private static final float MIN_HEIGHT = 111;
    @Override
    public void start(Stage stage) throws Exception {

        Parent p = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        Scene s = new Scene(p);
        stage.setScene(s);
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png")));
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
