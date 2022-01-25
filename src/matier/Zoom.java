package matier;

import callbacks.ZoomListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.AnimationHelper;

public class Zoom {

    private HBox anchorPane;
    private FontAwesomeIconView zoomIn;
    private FontAwesomeIconView zoomOut;
    private Label zoomLabel;
    private javafx.scene.control.Button reset;
    private ZoomListener zoomListener;
    private boolean showed = false;
    private double zoom=1;
    public Zoom(ZoomListener zoomListener) {
        this.zoomListener = zoomListener;
        init();
        anchorPane.setVisible(false);
    }
    public void setLeftTop(double right,double top){
        AnchorPane.setLeftAnchor(anchorPane,right);
        AnchorPane.setTopAnchor(anchorPane,top);
    }
    public void show(){
        anchorPane.setVisible(true);
        anchorPane.setTranslateY(-anchorPane.getHeight()/4);
        showed=true;
        AnimationHelper.showAnim(anchorPane.translateYProperty(),0);
    }
    public void hide(){
        AnimationHelper.hideAnim(anchorPane.translateYProperty(), -anchorPane.getHeight()/4,()->{
            anchorPane.setVisible(false);
            showed=false;
        });

    }

    public boolean isShowed() {
        return showed;
    }

    private void init(){
        anchorPane = new HBox();
        anchorPane.getStylesheets().add("/presentation/css/zoom.css");
        anchorPane.setPrefHeight(46);
        anchorPane.setPrefWidth(200);
        anchorPane.setSpacing(10);
        anchorPane.setPadding(new Insets(0,0,0,10));
        anchorPane.setAlignment(Pos.CENTER_LEFT);

        zoomIn = createIcon("PLUS");

        zoomOut = createIcon("MINUS");
        zoomLabel = new Label("100%");
        zoomLabel.setPadding(new Insets(0,0,0,10));
        reset = new Button("Reset");

        zoomIn.setOnMouseClicked((e)->{if(zoomListener!=null)zoomListener.onZoomIn();});
        zoomOut.setOnMouseClicked((e)->{if(zoomListener!=null)zoomListener.onZoomOut();});
        reset.setOnAction((e)->{if(zoomListener!=null)zoomListener.onReset();});
        anchorPane.getChildren().addAll(zoomIn,zoomOut,zoomLabel, reset);

    }
    public void updateZoom(double zoom){
        zoomLabel.setText(((int)(zoom*100))+"%");
        if (zoom<2)
            zoomIn.setFill(Color.valueOf("#000000"));
        else
            zoomIn.setFill(Color.valueOf("#DDDDDD"));

        if(zoom>.25)
            zoomOut.setFill(Color.valueOf("#000000"));
        else
            zoomOut.setFill(Color.valueOf("#DDDDDD"));
    }
    public Parent getContent(){return this.anchorPane;}
    private FontAwesomeIconView createIcon(String glyphName){
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setGlyphName(glyphName);
        icon.setSize(15+"");
        return icon;
    }
}
