package presentation.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TabPane tabPane;
    @FXML
    Button newTabBtn;

    private double newTabLeftPadding = 8.0D;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createNewTab();
    }

    @FXML
    private void newTab(ActionEvent actionEvent){
        createNewTab();
    }

    private void createNewTab(){
        Tab tab = new Tab("New Tab",new TextArea());
        tab.setOnClosed((event)->{
            this.newTabBtnPosLeft();
        });
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        this.newTabBtnPosRight();

    }
    private void newTabBtnPosRight() {
        this.newTabLeftPadding += 91;
        AnchorPane.setLeftAnchor(this.newTabBtn, Double.valueOf((double)(this.newTabLeftPadding++)));

    }

    private void newTabBtnPosLeft() {
        this.newTabLeftPadding -= 91;
        AnchorPane.setLeftAnchor(this.newTabBtn, Double.valueOf((double)(this.newTabLeftPadding--)));
        if (this.newTabLeftPadding < 100.0D) {
            System.out.println("All tabs closed.");
            Platform.exit();
        }

    }


}
