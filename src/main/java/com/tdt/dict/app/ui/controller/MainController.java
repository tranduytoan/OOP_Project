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
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Objects;


public class MainController extends WindowController {
    private static MainController instance;
    private Parent basicDict;
    private Parent gtrans;
    @FXML
    private BorderPane borderPane;
    private ArrayList<Stage> stages = new ArrayList<>();

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

    @Override
    public void handleClose(ActionEvent event) {
        for (Stage stage : stages) {
            stage.close();
        }
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void switchGtrans(ActionEvent event) throws Exception {
        borderPane.setCenter(gtrans);
    }

    public void switchBasicDict(ActionEvent event) throws Exception {
        borderPane.setCenter(basicDict);
    }

    public void openAddWordWindow(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stages.add(stage);
        stage.setTitle("Add Word");
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addWord.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void openExportWindow(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        stages.add(stage);
        stage.setTitle("Export");
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exportAllWord.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void removeStage(Stage stage) {
        stages.remove(stage);
    }

    public void addStage(Stage stage) {
        stages.add(stage);
    }
}