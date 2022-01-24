package util;

import javafx.fxml.FXMLLoader;

public class FileLoader {


    public static FXMLLoader loadXml(String fileName){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(FileLoader.class.getResource("/presentation/fxml/"+fileName+".fxml"));
        return fxmlLoader;
    }
}
