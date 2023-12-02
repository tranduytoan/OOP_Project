package com.tdt.dict.app.ui.controller;

import com.tdt.dict.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

public class BasicDictionaryController {
    @FXML
    private TextField wordInput;
    @FXML
    private WebView wordDefine;

    public void search() {
        String word = wordInput.getText();
        String define = App.search(word);
        wordDefine.getEngine().loadContent(define);
    }
}
