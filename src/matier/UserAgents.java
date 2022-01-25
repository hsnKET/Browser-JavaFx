package matier;

import callbacks.UserAgentListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.AnimationHelper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAgents {
    private AnchorPane parent;
    private FontAwesomeIconView close;
    private ListView<VBox> listView;
    private ObservableList<VBox> userAgentItems;
    private List<UserAgent> userAgents;
    private boolean isShowed = false;
    private UserAgentListener userAgentListener;
    public UserAgents(UserAgentListener userAgentListener) {
        this.userAgentListener = userAgentListener;
        parent = new AnchorPane();
        close= new FontAwesomeIconView();
        close.setGlyphName("CLOSE");
        close.setFill(Color.valueOf("#FF0000"));
        close.setSize(14+"");

        Button bClose=new Button("",close);
        bClose.setMaxWidth(15);
        bClose.setMaxHeight(15);
        bClose.setPadding(new Insets(0,4,0,4));
        bClose.setStyle("-fx-background-color: #EEEEEE;-fx-border-radius: 100;-fx-background-radius: 100");
        bClose.setOnAction((e)->{
            hide();
        });

        AnchorPane.setTopAnchor(bClose,-10.00);
        AnchorPane.setLeftAnchor(bClose,-10.00);

        listView=new ListView<>();
        AnchorPane.setRightAnchor(listView,0.00);
        AnchorPane.setBottomAnchor(listView,0.00);
        AnchorPane.setTopAnchor(listView,0.00);

        parent.getChildren().addAll(listView,bClose);
        //parent.getStylesheets().add("UserAgent.css");
        userAgents=new ArrayList<>();

        load();
        System.out.println("user agent : "+userAgents.size());
        userAgentItems = listView.getItems();

        for(UserAgent u: userAgents)
            userAgentItems.add(createItem(u));
        parent.setVisible(false);
    }

    public Parent getContent() {
        return this.parent;
    }


    private VBox createItem(UserAgent userAgent) {

        VBox item =new VBox();
        item.getStyleClass().add("item-user-agent");
        AnchorPane anchorPaneHead=new AnchorPane();
        anchorPaneHead.setStyle("-fx-alignment: Center");
        Text text=new Text(userAgent.getTitle());
        text.getStyleClass().add("nom");

        TextField textField=new TextField(userAgent.getAgent());
        textField.setDisable(true);
        textField.getStyleClass().add("text-field-userAgent-disable");


        FontAwesomeIconView btnDelete=new FontAwesomeIconView();
        btnDelete.setGlyphName("TRASH");
        btnDelete.setFill(Color.RED);
        FontAwesomeIconView btnEdit=new FontAwesomeIconView();
        btnEdit.setGlyphName("PENCIL");
        btnEdit.setFill(Color.BLUE);
        FontAwesomeIconView btnSave=new FontAwesomeIconView();
        btnSave.setGlyphName("SAVE");
        HBox containerBtn=new HBox();
        containerBtn.setSpacing(10);

        containerBtn.getChildren().addAll(btnEdit,btnSave,btnDelete);

        btnDelete.setSize(15+"");
        btnEdit.setSize(15+"");
        btnSave.setSize(15+"");
        btnEdit.setOnMouseClicked(event -> {
            textField.setDisable(false);
            textField.getStyleClass().removeIf(style -> style.equals("text-field-userAgent-disable"));
        });

        btnSave.setFill(Color.GREEN);

        btnSave.setOnMouseClicked(event -> {
            textField.setDisable(true);
            textField.getStyleClass().add("text-field-userAgent-disable");
            userAgent.setAgent(textField.getText());
            saveAll();
        });;

        AnchorPane.setRightAnchor(containerBtn,10.00);
        AnchorPane.setTopAnchor(containerBtn,5.00);
        AnchorPane.setLeftAnchor(text,5.00);
        AnchorPane.setTopAnchor(text,9.00);
        anchorPaneHead.getChildren().addAll(text,containerBtn);


        item.getChildren().addAll(anchorPaneHead,textField);



        btnDelete.setOnMouseClicked(event -> {
            userAgentItems.remove(userAgents.indexOf(userAgent));
            userAgents.remove(userAgent);
            saveAll();
        });

        parent.getStylesheets().add("/presentation/css/item_user_agent.css");

        item.setOnMouseClicked((e)->{
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() == 2){
                    hide();
                    for (UserAgent u:userAgents)
                        u.setChecked(false);
                    userAgent.setChecked(true);
                    saveAll();
                    if (userAgentListener!=null)
                        userAgentListener.onUserAgentChecked(userAgent);

                }
            }
        });

        return item;

    }
    public void show() {
        if (isShowed)return;
        isShowed = true;
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
    public void load() {

        File file = new File("user_agent.bat");
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object object = objectInputStream.readObject();
                if (object instanceof List) userAgents.addAll((List) object);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (userAgents.size()==0 || !file.exists())
            userAgents.add(getDefaultUserAgent());


    }

    private UserAgent getDefaultUserAgent(){
        UserAgent defaultUserAgent = new UserAgent("Chrome - Default","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
        defaultUserAgent.setChecked(true);
        return defaultUserAgent;
    }
    public UserAgent getCheckedUserAgent(){
        for(UserAgent u : userAgents)
            if(u.isChecked())return u ;
        return getDefaultUserAgent();
    }
    public void saveAll() {
        try {
            File file = new File("user_agent.bat");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userAgents);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
