package com.tdt.dict.app.ui.controller;

import com.tdt.dict.app.core.BasicDictionary;
import com.tdt.dict.app.core.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExportAllWordController extends WindowController {
    @FXML
    private Button btnExport;
    @FXML
    private Button btnBrowse;
    @FXML
    private TextField DirName;
    @FXML
    private TextField fileName;
    private String dirPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnExport.setDisable(true);
        DirName.setStyle("-fx-border-color: red");
        DirName.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.isEmpty()) {
                DirName.setStyle("-fx-border-color: red");
                btnExport.setDisable(true);
            } else {
                DirName.setStyle("-fx-border-color: none");
                btnExport.setDisable(fileName.getText().isEmpty());
            }
        });
        fileName.setStyle("-fx-border-color: red");
        fileName.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.isEmpty()) {
                fileName.setStyle("-fx-border-color: red");
                btnExport.setDisable(true);
            } else {
                fileName.setStyle("-fx-border-color: none");
                btnExport.setDisable(DirName.getText().isEmpty());
            }
        });
    }

    public String browseDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory == null) {
            return ""; // No directory selected
        }

        return selectedDirectory.getAbsolutePath();
    }

    public void handleBrowse() {
        dirPath = browseDir();
        DirName.setText(dirPath);
    }

    public void handleExport() {
        String path = dirPath + "/" + fileName.getText() + ".txt";
        ArrayList<Word> words = BasicDictionary.getInstance().getAllWords();
        File file = new File(path);
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Word word : words) {
                writer.println(word.getWordTarget() + " - " + word.getWordExplain());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Export successfully");
            alert.setContentText("File is saved at " + path);
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while writing to the file.");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }

    @Override
    public void handleClose(ActionEvent event) {
        Stage stage = (Stage) btnBrowse.getScene().getWindow();
        MainController.getInstance().removeStage(stage);
        stage.close();
    }
}
