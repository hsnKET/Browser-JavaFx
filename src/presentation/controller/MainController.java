package presentation.controller;

import callbacks.TabListenerState;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import matier.TabView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, TabListenerState {

    @FXML
    private FontAwesomeIconView bntNewTab;

    @FXML
    private TabPane tabPane;

    private double newTabLeftPadding = 12;
    private double SHIFTED_WIDTH = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane.setTabMaxHeight(25);
        tabPane.setTabMinHeight(25);
        tabPane.setTabMinWidth(100);
        tabPane.setTabMaxWidth(100);
        createNewTab();
        bntNewTab.setOnMouseClicked((e)->{
            createNewTab();
        });

    }

    private void createNewTab(){
        this.SHIFTED_WIDTH = (tabPane.getTabMaxWidth())+12;
        TabView tabView = new TabView("New Tab");
        tabPane.getTabs().add(tabView.getTab());
        tabView.setTabListener(this);
        newTabBtnPosRight();
    }


    @Override
    public void onTabClosed() {
        ShiftNewTabBtnPosLeft();
    }

    private void newTabBtnPosRight(){
        newTabLeftPadding += SHIFTED_WIDTH;
        AnchorPane.setLeftAnchor(bntNewTab, newTabLeftPadding++);
    }

    private void ShiftNewTabBtnPosLeft(){
        newTabLeftPadding -= SHIFTED_WIDTH;
        AnchorPane.setLeftAnchor(bntNewTab, newTabLeftPadding--);
        if(newTabLeftPadding < (12+SHIFTED_WIDTH)){
            Platform.exit();
        }
    }

}
