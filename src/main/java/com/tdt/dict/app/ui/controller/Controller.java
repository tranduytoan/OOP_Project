package com.tdt.dict.app.ui.controller;

import com.tdt.dict.App;
import com.tdt.dict.app.core.Database;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Controller {

    @FXML
    private WebView myWebView;
    @FXML
    private TextField inputField;

    private WebEngine engine;

    @FXML
    public void search() {
        Database.word = inputField.getText();
        String define = App.search(Database.word);
        engine = myWebView.getEngine();
        engine.loadContent(define);
    }
}