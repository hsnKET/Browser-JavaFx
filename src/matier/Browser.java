package matier;

import callbacks.BrowserState;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Browser {

    private WebView webView;
    private WebEngine webEngine;
    private WebHistory history;
    private BrowserState browserState;
    private TabView tabView;

    public Browser(TabView tab){
        this.tabView  = tab;
        createWebView();
        webEngine = webView.getEngine();
        history = webEngine.getHistory();



    }

    private void createWebView(){
        webView = new WebView();
        webView.setId("webView");
        AnchorPane.setLeftAnchor(webView,0d);
        AnchorPane.setRightAnchor(webView,0d);
        AnchorPane.setTopAnchor(webView,40d);
        AnchorPane.setBottomAnchor(webView,0d);
        webView.setLayoutY(40);
        webView.setLayoutY(0);

    }
    public WebView getWebView(){return this.webView;}
    public void setBrowserState(BrowserState browserState) {
        this.browserState = browserState;
    }

    public void load(String url){
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        //set title to tabview
                        tabView.getTab().setText(webEngine.getTitle());
                        //update btn back forward status
                        checkForwardBackState();
                        ProgressIndicator progressIndicator = new ProgressIndicator(-1.0);
                        progressIndicator.setPrefHeight(17);
                        progressIndicator.setPrefWidth(25);
                        tabView.getTab().setGraphic(progressIndicator);

                        if(browserState!=null){
                            browserState.onPageLoading(webEngine);
                            browserState.onSecureStateChange((webEngine.getLocation()!=null && webEngine.getLocation().startsWith("https")));
                        }

                        if (newState == Worker.State.SUCCEEDED ||
                                newState == Worker.State.CANCELLED ||
                                newState == Worker.State.FAILED) {
                            if(browserState!=null)
                                browserState.onPageLoaded(webEngine);
                                tabView.getTab().setText(webEngine.getTitle());
                                tabView.getTab().setGraphic(loadFavicon(webEngine.getLocation()));

                        }
                    }
                });
        webEngine.load(url);
    }

    private void checkForwardBackState(){
            if(browserState!=null){
                browserState.onBackForwardStateChange(canForward(),canBack());
            }
    }
    public void goBack(){

        Platform.runLater(() ->
        {
            history.go(canBack()? -1 : 0);
        });
    }
    public void goForward(){

        Platform.runLater(() ->
        {
            history.go( canForward()? 1 : 0);
        });
    }
    private boolean canForward(){
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        return entryList.size() > 1 && currentIndex < entryList.size() - 1;
    }
    private boolean canBack(){
        ObservableList<WebHistory.Entry> entryList = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        return (entryList.size() > 1 && currentIndex > 0 );
    }
    public void reload(){
        webEngine.reload();
    }
    public void stopLoading(){
        webEngine.executeScript("window.stop()");
    }

    public boolean canZoomIn(){
        return  webView.getZoom()<2 ;
    }
    public boolean canZoomOut(){
        return webView.getZoom()>.25;
    }
    public void zoomIn(){
        this.webView.setZoom(canZoomIn()?webView.getZoom()+0.25:webView.getZoom());
    }
    public void zoomOut(){
        this.webView.setZoom(canZoomOut()?webView.getZoom()-0.25:webView.getZoom());
    }
    public void resetZoom(){
        this.webView.setZoom(1.0);
    }
    public ImageView loadFavicon(String location) {
        try {
            String faviconUrl;
            if(webEngine.getTitle().equalsIgnoreCase("Google")){
                faviconUrl = "https://www.google.com/s2/favicons?domain_url=www.gmail.com";
            } else{
                faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(location, "UTF-8"));
            }
            Image favicon = new Image(faviconUrl, true);
            if(favicon.equals(new Image("http://www.google.com/s2/favicons?domain_url=abc"))){
                ImageView iv_default = new ImageView(new Image("file:Resources/presentation.home.png"));
                return iv_default;
            }else{
                ImageView iv = new ImageView(favicon);
                return iv;
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
