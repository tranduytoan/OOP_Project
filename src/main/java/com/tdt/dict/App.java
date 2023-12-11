package com.tdt.dict;

import com.tdt.dict.app.core.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        database.Connect();
        launch();
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader app = new FXMLLoader(App.class.getResource("/com/tdt/dict/app/ui/controller/main.fxml"));
        Scene scene = new Scene(app.load());
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/main.css")).toExternalForm());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("MyDictionary");
        stage.setResizable(false);
        stage.show();
    }
}
