package matier;


import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;


public class Menu  {

    private MenuListener menuListener;
    private ContextMenu contextMenu;

    public Menu(MenuListener menuListener){

        contextMenu = new ContextMenu();
        this.menuListener = menuListener;
        MenuItem itemFavorites = new MenuItem("Favorites");
        MenuItem itemUserAgent= new MenuItem("User Agent");
        MenuItem itemDownloads = new MenuItem("Downloads");
        MenuItem itemHistory = new MenuItem("History");
        MenuItem itemCookies= new MenuItem("Cookies");

        itemHistory.setOnAction((e)->{
            menuListener.onHistoryClicked();
        });itemFavorites.setOnAction((e)->{
            menuListener.onFavoriteClicked();
        });itemUserAgent.setOnAction((e)->{
            menuListener.onUserAgentClicked();
        });itemCookies.setOnAction((e)->{
            menuListener.onUserCookieClicked();
        });

        contextMenu.getItems().addAll(itemFavorites,itemUserAgent,new SeparatorMenuItem(),itemDownloads,itemHistory,itemCookies);

    }


    public ContextMenu getContextMenu(){return this.contextMenu;}
    public void show(Node node, double x,double y){

        contextMenu.show(node,x,y);

    }
    public interface MenuListener{
        void onHistoryClicked();
        void onFavoriteClicked();
        void onDownloadClicked();
        void onUserAgentClicked();
        void onUserCookieClicked();
    }

}
