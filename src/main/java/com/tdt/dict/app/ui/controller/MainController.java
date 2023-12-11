package com.tdt.dict.app.ui.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
}