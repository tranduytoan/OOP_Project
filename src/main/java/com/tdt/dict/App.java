package com.tdt.dict;

import com.tdt.dict.app.core.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Database database = new Database();

    public static void main(String[] args) {
        database.Connect();
        launch();
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader test = new FXMLLoader(App.class.getResource("/fxml/test.fxml"));
        Scene scene = new Scene(test.load());
        stage.setScene(scene);
        stage.setTitle("Test");
        stage.setResizable(false);
        stage.show();
    }

    public static String search(String word) {
        return database.Search(word);
    }
}
