package com.tdt.dict.app.ui.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainController implements Initializable {
    private static MainController instance;
    protected double xOffset = 0;
    protected double yOffset = 0;

    @FXML
    private Button btClose;
    @FXML
    private Button btMin;
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
            FXMLLoader scene1 = new FXMLLoader(getClass().getResource("basicDictionary.fxml"));
            borderPane.setCenter(scene1.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClose(ActionEvent event) {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    public void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) btMin.getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleDrag(MouseEvent event) {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void handleClicked(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void handlePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void setBorderPaneCenter(FXMLLoader loader) throws Exception {
        this.borderPane.setCenter(loader.load());
    }
}