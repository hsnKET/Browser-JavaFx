package matier;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import callbacks.*;
public class TabView implements BrowserState,ZoomListener{


    private Tab tab;
    private String title;
    private AnchorPane parent;
    private HBox hBox;
    private FontAwesomeIconView btnBack;
    private FontAwesomeIconView btnForward;
    private FontAwesomeIconView btnReload;
    private StackPane stackPane;
    private TextField tfSearch;
    private FontAwesomeIconView btnSafe;
    private FontAwesomeIconView btnZoom;
    private FontAwesomeIconView btnStar;
    private FontAwesomeIconView btnMenu;
    private TabListenerState mTabListenerState;
    private Browser browser;
    private BrowserState browserState;
    private Zoom zoom ;

    public TabView(String title){
        this.title = title;
        createTab();
        initTab();
        browser.setBrowserState(this);
        browser.load("https://www.google.com");

    }

    private void createTab(){
        tab = new Tab(title);

        parent = new AnchorPane();
        parent.setPrefHeight(450);
        parent.setPrefWidth(800);
        parent.getStylesheets().add("/presentation/css/tabview.css");
        //create hBox
        createHBox();
        browser = new Browser(this);
        parent.getChildren().add(browser.getWebView());
        addEventsHandler();
        tab.setContent(parent);
        zoom = new Zoom(this);
        parent.getChildren().add(zoom.getContent());
        btnZoom.setOnMouseClicked((e)->{
            System.out.println(btnZoom.getLayoutX());
            zoom.setLeftTop(btnZoom.getLayoutX()-zoom.getContent().getBoundsInParent().getWidth(),hBox.getHeight()+1);
            if(zoom.isShowed()){
                zoom.hide();
            }else{
                zoom.show();
            }
        });

    }
    private void createHBox(){
        hBox = new HBox();
        hBox.setId("toolbarHBox");
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPrefHeight(40);
        hBox.setSpacing(10);
        AnchorPane.setLeftAnchor(hBox,0d);
        AnchorPane.setRightAnchor(hBox,0d);
        AnchorPane.setTopAnchor(hBox,0d);
        //add padding to left and right
        hBox.setPadding(new Insets(0,15,0,15));
        //create buttons
        btnBack = createIcon("ARROW_LEFT");
        btnForward = createIcon("ARROW_RIGHT");
        btnReload = createIcon("REFRESH");
        tfSearch = createSearchField();
        tfSearch.setId("tfSearch");
        btnZoom = createIcon("SEARCH_PLUS");
        btnStar = createIcon("STAR_ALT");
        btnMenu = createIcon("ELLIPSIS_V");
        //add buttons to hBox
        hBox.getChildren().addAll(btnBack,btnForward,btnReload,stackPane,btnZoom,btnStar,btnMenu);
        //add to AnchorPane
        parent.getChildren().add(hBox);

    }

    private FontAwesomeIconView createIcon(String glyphName){
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setGlyphName(glyphName);
        icon.setSize(16+"");
        return icon;
    }
    private TextField createSearchField(){

        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(stackPane,Priority.ALWAYS);
        btnSafe = createIcon("LOCK");
        btnSafe.setFill(Color.valueOf("#00FF00"));
        btnSafe.setTranslateX(8);
        TextField textField = new TextField();
        textField.setPrefHeight(25);
        textField.setPrefWidth(0);
        btnSafe.setFill(Color.valueOf("#00FF00"));
        btnSafe.setTranslateX(8);
        stackPane.getChildren().addAll(textField,btnSafe);
        return textField;
    }
    private boolean isUrl(String url){
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
    private void addEventsHandler(){
        tfSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(KeyCode.ENTER.equals(event.getCode())){
                    String baseUrl = "https://www.google.com/search?q=";
                    try {
                        browser.load(isUrl(tfSearch.getText())?tfSearch.getText(): baseUrl+URLEncoder.encode(tfSearch.getText(), StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {

                    }
                }

            }
        });
        btnBack.setOnMouseClicked((e)->{
            browser.goBack();
        });
        btnForward.setOnMouseClicked((e)->{
            browser.goForward();
        });
        btnReload.setOnMouseClicked((e)->{
            browser.reload();
        });
        btnStar.setOnMouseClicked((e)->{

        });
        btnMenu.setOnMouseClicked((e)->{

        });
    }
    private void initTab(){
        this.tab.setOnClosed((e)->{
            if(mTabListenerState !=null)
                mTabListenerState.onTabClosed();
        });
        this.tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab.isSelected())
                    tab.setStyle("-fx-background-color:white");
                else tab.setStyle("-fx-background-color:#EEEEFF");
            }
        });


    }
    public Tab getTab(){
        return this.tab;
    }

    public void setTabListener(TabListenerState tabListenerState) {
        mTabListenerState = tabListenerState;
    }
    public void setBrowserState(BrowserState browserState) {this.browserState = browserState;}


    @Override
    public void onPageLoading(WebEngine webEngine) {
        btnReload.setGlyphName("CLOSE");
        tfSearch.setText(webEngine.getLocation());
        btnReload.setOnMouseClicked((e) -> {
            browser.stopLoading();
        });
    }

    @Override
    public void onPageLoaded(WebEngine webEngine) {
        tfSearch.setText(webEngine.getLocation());
        btnReload.setGlyphName("REFRESH");
        btnReload.setOnMouseClicked((e) -> {
            browser.reload();
        });
    }

    @Override
    public void onBackForwardStateChange(boolean canForward, boolean canBack) {
        if (canForward){
            btnForward.setDisable(false);
            btnForward.setFill(Color.valueOf("#000000"));
        }else {
            btnForward.setDisable(true);
            btnForward.setFill(Color.valueOf("#EEEEEE"));
        }
        if(canBack){
            btnBack.setDisable(false);
            btnBack.setFill(Color.valueOf("#000000"));
        }else {
            btnBack.setDisable(true);
            btnBack.setFill(Color.valueOf("#EEEEEE"));
        }
    }

    @Override
    public void onSecureStateChange(boolean isHttps) {
        if (isHttps){
            btnSafe.setGlyphName("LOCK");
            btnSafe.setFill(Color.valueOf("#00FF00"));
        }else {
            btnSafe.setGlyphName("UNLOCK");
            btnSafe.setFill(Color.valueOf("#FF0000"));
        }
    }

    @Override
    public void onZoomIn() {
        if (browser.canZoomIn()){
            zoom.updateZoom(browser.getWebView().getZoom()+.25);
            browser.zoomIn();
        }
    }

    @Override
    public void onZoomOut() {
        if (browser.canZoomOut()){
            zoom.updateZoom(browser.getWebView().getZoom()-.25);
            browser.zoomOut();
        }
    }

    @Override
    public void onReset() {
        zoom.updateZoom(1);
        browser.resetZoom();
    }
}
