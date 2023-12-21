package com.tdt.dict.app.ui.controller;

import com.tdt.dict.MultiThreading;
import com.tdt.dict.app.core.BasicDictionary;
import com.tdt.dict.app.core.Database;
import com.tdt.dict.app.core.GoogleTranslate;
import com.tdt.dict.app.core.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BasicDictionaryController implements Initializable {
    @FXML
    private TextField wordInput;
    @FXML
    private WebView wordDefine;
    @FXML
    private ListView<String> suggestWords;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnTts;
    @FXML
    private Button btnAddNote;
    private Scene editWord;
    private BasicDictionary basicDictionary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader editW = new FXMLLoader(getClass().getResource("editWord.fxml"));
            editWord = new Scene(editW.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        basicDictionary = BasicDictionary.getInstance();
        wordInput.textProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<String> words = basicDictionary.getTrie().getWordsStartsWith(newValue);
            ObservableList<String> list = FXCollections.observableArrayList(words);
            suggestWords.setItems(list);
        });
        suggestWords.setOnMouseClicked((MouseEvent event) -> {
            String selectedWord = suggestWords.getSelectionModel().getSelectedItem();
            wordInput.setText(selectedWord);
            search();
        });

        // set visible for buttons
        btnTts.setVisible(false);
        btnAddNote.setVisible(false);
        btnDelete.setVisible(false);
        btnEdit.setVisible(false);

        // log
        System.out.println("BasicDictionaryController initialized");
    }

    public void search() {
        String word = wordInput.getText();
        String define = basicDictionary.search(word);
        if (!define.equals("Not found")) {
            btnDelete.setVisible(true);
            btnEdit.setVisible(true);
            btnTts.setVisible(true);
            btnAddNote.setVisible(true);
        } else {
            btnDelete.setVisible(false);
            btnEdit.setVisible(false);
            btnTts.setVisible(false);
            btnAddNote.setVisible(false);
        }
        String content = "<html><body contenteditable='false'>" + define + "</body></html>";
        wordDefine.getEngine().loadContent(content);
    }

    public void clearSearch() {
        wordInput.setText("");
        wordDefine.getEngine().loadContent("");
        btnDelete.setVisible(false);
        btnEdit.setVisible(false);
        btnTts.setVisible(false);
        btnAddNote.setVisible(false);
    }

    public void textToSpeech() {
        GoogleTranslate.speak(wordInput.getText(), GoogleTranslate.lang.ENGLISH);
    }

    public void deleteWord() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete word");
        alert.setHeaderText("Delete word");
        alert.setContentText("Do you want to deleted word: " + wordInput.getText() + "?");
        alert.showAndWait();

        if (alert.getResult().getText().equals("OK")) {
            String word = wordInput.getText();
            basicDictionary.delete(new Word(word));
            wordInput.setText("");
            wordDefine.getEngine().loadContent("");
        } else {
            alert.close();
        }
    }

    public void editWord() throws IOException {
        Word word = Database.getInstance().Search(wordInput.getText());
        Stage stage = new Stage();
        MainController.getInstance().addStage(stage);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Edit Word" + word.getWordTarget());
        stage.setScene(editWord);
        EditWordController.getInstance().init(word);
        stage.show();

        stage.setOnCloseRequest(event -> {
            if (EditWordController.getInstance().checkChange()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("Are you sure you want to exit without saving?");
                alert.setContentText("All changes will be lost");
                alert.showAndWait();
                if (alert.getResult().getText().equals("OK")) {
                    stage.close();
                } else {
                    event.consume();
                }
            }
        });

        stage.setOnHidden(event -> {
            search();
        });
    }
}
