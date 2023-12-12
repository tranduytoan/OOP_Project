package com.tdt.dict.app.ui.controller;

import com.tdt.dict.app.core.BasicDictionary;
import com.tdt.dict.app.core.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController extends WindowController {
    @FXML
    private TextField wordTarget;
    @FXML
    private TextField pronounce;
    @FXML
    private TextArea description;
    @FXML
    private HTMLEditor html;
    @FXML
    private Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setDisable(true);
        wordTarget.setStyle("-fx-border-color: red");
        description.setStyle("-fx-border-color: red");
        wordTarget.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.isEmpty()) {
                wordTarget.setStyle("-fx-border-color: red");
                btnSave.setDisable(true);
            } else {
                wordTarget.setStyle("-fx-border-color: none");
                btnSave.setDisable(description.getText().isEmpty());
            }
        });
        description.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.isEmpty()) {
                description.setStyle("-fx-border-color: red");
                btnSave.setDisable(true);
            } else {
                description.setStyle("-fx-border-color: none");
                btnSave.setDisable(wordTarget.getText().isEmpty());
            }
        });
    }

    public boolean wordIsExist() {
        return BasicDictionary.getInstance().getTrie().search(wordTarget.getText());
    }

    public void handleSave() {
        if (wordIsExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Word is already exist");
            alert.setContentText("Please choose another word");
            alert.showAndWait();
            return;
        }
        Word word = new Word();
        word.setWordTarget(wordTarget.getText());
        word.setWordExplain(description.getText());
        word.setWordHtml(html.getHtmlText());
        word.setWordPronounce(pronounce.getText());
        BasicDictionary.getInstance().insert(word);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Add word successfully");
        alert.setContentText("Word: " + wordTarget.getText());
        alert.showAndWait();
    }

    @Override
    public void handleClose(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Do you want to exit?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            super.handleClose(event);
        } else {
            event.consume();
        }
    }
}