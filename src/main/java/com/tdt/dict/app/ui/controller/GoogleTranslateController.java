package com.tdt.dict.app.ui.controller;

import com.tdt.dict.app.core.GoogleTranslate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GoogleTranslateController implements Initializable {
    Mode mode;
    @FXML
    private Button btnTranslate;
    @FXML
    private Button btnSpeak1;
    @FXML
    private Button btnSpeak2;
    @FXML
    private Button btnSwitch;
    @FXML
    private TextArea textTarget;
    @FXML
    private TextArea textDefine;
    @FXML
    private Text ltl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ltl.setText("English to Vietnamese");
        mode = Mode.EN_VI;
    }

    public void switchLanguage() {
        if (mode == Mode.EN_VI) {
            ltl.setText("Vietnamese to English");
            String tmp = textTarget.getText();
            textTarget.setText(textDefine.getText());
            textDefine.setText(tmp);
            mode = Mode.VI_EN;
        } else {
            ltl.setText("English to Vietnamese");
            String tmp = textTarget.getText();
            textTarget.setText(textDefine.getText());
            textDefine.setText(tmp);
            mode = Mode.EN_VI;
        }
    }

    public void translate() throws IOException {
        if (mode == Mode.EN_VI) {
            textDefine.setText(GoogleTranslate.translate(GoogleTranslate.lang.ENGLISH,
                    GoogleTranslate.lang.VIETNAMESE,
                    textTarget.getText()));
        } else {
            textDefine.setText(GoogleTranslate.translate(GoogleTranslate.lang.VIETNAMESE,
                    GoogleTranslate.lang.ENGLISH,
                    textTarget.getText()));
        }
    }

    public void speak1() throws IOException {
        if (textTarget.getText().length() > 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Text too long for text to speech");
            alert.setContentText("Maximum length is 200 characters");
            alert.showAndWait();
            return;
        }
        if (mode == Mode.EN_VI) {
            GoogleTranslate.speak(textTarget.getText(), GoogleTranslate.lang.ENGLISH);
        } else {
            GoogleTranslate.speak(textTarget.getText(), GoogleTranslate.lang.VIETNAMESE);
        }
    }

    public void speak2() throws IOException {
        if (textDefine.getText().length() > 200) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Text too long for text to speech");
            alert.setContentText("Maximum length is 200 characters");
            alert.showAndWait();
            return;
        }
        if (mode == Mode.EN_VI) {
            GoogleTranslate.speak(textDefine.getText(), GoogleTranslate.lang.VIETNAMESE);
        } else {
            GoogleTranslate.speak(textDefine.getText(), GoogleTranslate.lang.ENGLISH);
        }
    }

    public enum Mode {
        EN_VI,
        VI_EN
    }
}
