package com.tdt.dict.app.ui.controller;

import com.tdt.dict.app.core.BasicDictionary;
import com.tdt.dict.app.core.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditWordController extends WindowController {
    private static EditWordController instance;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    private Word word;

    public EditWordController() {
        instance = this;
    }

    public static EditWordController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void handleClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        if (checkChange()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Do you want to save before exit?");
            alert.setContentText("Some changes have not been saved");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                word.setWordHtml(htmlEditor.getHtmlText());
                BasicDictionary.getInstance().edit(word);
                stage.close();
            } else {
                stage.close();
            }
        } else {
            stage.close();
        }

    }

    public void init(Word word) {
        this.word = word;
        htmlEditor.setHtmlText(word.getWordHtml());
    }

    public void setHtmlEditor(HTMLEditor htmlEditor) {
        this.htmlEditor = htmlEditor;
    }

    public HTMLEditor getHtmlEditor() {
        return htmlEditor;
    }

    public void handleSave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to save and exit?");
        alert.setContentText("All changes will be saved");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            word.setWordHtml(htmlEditor.getHtmlText());
            BasicDictionary.getInstance().edit(word);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
    }

    public void handleCancel(ActionEvent event) {
        if (!checkChange()) {
            event.consume();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("All changes will be lost");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                htmlEditor.setHtmlText(word.getWordHtml());
            } else {
                event.consume();
            }
        }


    }

    public boolean checkChange() {
        if (htmlEditor.getHtmlText().equals(word.getWordHtml())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("No changes");
            alert.setContentText("You have not made any changes");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
