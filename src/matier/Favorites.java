package matier;


import callbacks.SideBarListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import util.AnimationHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Favorites {

    private AnchorPane parent;
    private FontAwesomeIconView close;
    private ListView<AnchorPane> listView;
    private ObservableList favoriteItems;
    private List<Favorite> favorites;
    private boolean isShowed = false;
    private SideBarListener favoritesListener;
    public Favorites(SideBarListener favoritesListener) {
        this.favoritesListener  = favoritesListener;
        parent = new AnchorPane();
        close = new FontAwesomeIconView();
        close.setGlyphName("CLOSE");
        close.setFill(Color.valueOf("#FF0000"));
        close.setSize(14+"");
        close.setOnMouseClicked((e)->{
            hide();
        });
        Button bClose=new Button("",close);
        bClose.setMaxWidth(15);
        bClose.setMaxHeight(15);
        bClose.setPadding(new Insets(0,4,0,4));
        bClose.setStyle("-fx-background-color: #EEEEEE;-fx-border-radius: 100;-fx-background-radius: 100");

        AnchorPane.setTopAnchor(bClose,-7d);
        AnchorPane.setLeftAnchor(bClose,-6d);

        listView = new ListView<>();
        AnchorPane.setTopAnchor(listView,0d);
        AnchorPane.setRightAnchor(listView,0d);
        AnchorPane.setBottomAnchor(listView,0d);
        AnchorPane.setLeftAnchor(listView,0d);
        parent.getChildren().addAll(listView,bClose);

        favorites = new ArrayList<>();
        load();
        for(Favorite f: favorites)
            listView.getItems().add(createItem(f));
        favoriteItems = listView.getItems();
        hide();
    }

    public void show() {
        if (isShowed) {
            isShowed = false;
            parent.setVisible(false);
        } else {
            isShowed = true;
            parent.setVisible(true);
        }
        parent.setTranslateX(parent.getWidth());
        parent.setVisible(true);
        AnimationHelper.showAnim(parent.translateXProperty(),0);

    }
    public void hide() {
        AnimationHelper.hideAnim(parent.translateXProperty(), parent.getWidth(),()->{
            isShowed = false;
            parent.setVisible(false);
        });

    }
    public Parent getContent() {
        return this.parent;
    }

    private AnchorPane createItem(Favorite favorite) {
        AnchorPane item = new AnchorPane();
        item.getStylesheets().add("/presentation/css/item_favorite.css");

        VBox vBox = new VBox();
        Label titleLabel = new Label(favorite.getTitle());
        titleLabel.setMaxWidth(200);
        Label urlLabel = new Label(favorite.getUrl());
        urlLabel.setMaxWidth(200);
        vBox.getChildren().addAll(titleLabel, urlLabel);
        vBox.setMargin(urlLabel, new Insets(0, 0, 0, 1));

        FontAwesomeIconView delete = new FontAwesomeIconView();
        delete.setGlyphName("TRASH");
        delete.setFill(Color.RED);
        AnchorPane.setRightAnchor(delete, 10.00);
        AnchorPane.setTopAnchor(delete, 10d);
        AnchorPane.setLeftAnchor(vBox, 10.00);
        AnchorPane.setTopAnchor(vBox, 0d);
        item.getChildren().addAll(vBox, delete);
        vBox.setOnMouseClicked((e)->{
            favoritesListener.onItemClicked(favorite);
        });
        delete.setOnMouseClicked((e)->{
            delete(favorite);
            if (favoritesListener!=null)
                favoritesListener.onFavoriteItemRemoved(favorite);

        });
        return item;

    }
    public boolean isInFavorites(String url){
        for (int i=0;i<favorites.size();i++)
            if(url.equals(favorites.get(i).getUrl()))return true;
        return false;
    }
    public void add(Favorite f){
        favorites.add(f);
        favoriteItems.add(favorites.indexOf(f),createItem(f));
        saveAll();
    }
    public void delete(Favorite favorite){
        favoriteItems.remove(favorites.indexOf(favorite));
        favorites.remove(favorite);
        saveAll();
    }
    public void delete(String url){
        if (favorites==null)return;
        for (int i=0;i<favorites.size();i++){
            Favorite f = favorites.get(i);
            if (url.equals(f.getUrl())){
                favoriteItems.remove(favorites.indexOf(f));
                favorites.remove(f);
                saveAll();
            }
        }

    }
    public void load() {
        File file = new File("favorites.bat");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object object = objectInputStream.readObject();
                if (object instanceof List) favorites = (ArrayList<Favorite>) object;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void saveAll() {
        try {
            File file = new File("favorites.bat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(favorites);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}