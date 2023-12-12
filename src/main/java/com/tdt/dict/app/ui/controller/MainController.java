package com.tdt.dict.app.ui.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;


public class MainController extends WindowController {
    private static MainController instance;
    private Parent basicDict;
    private Parent gtrans;
    @FXML
    private BorderPane borderPane;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        try {
            basicDict = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("basicDict.fxml")));
            gtrans = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gtrans.fxml")));
            // default on start
            borderPane.setCenter(basicDict);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchGtrans(ActionEvent event) throws Exception {
        borderPane.setCenter(gtrans);
    }

    public void switchBasicDict(ActionEvent event) throws Exception {
        borderPane.setCenter(basicDict);
    }

    public void openAddWordWindow(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Add Word");
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addWord.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}