package com.tdt.dict.app.ui.controller;

import com.tdt.dict.App;
import com.tdt.dict.app.core.Database;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Scanner;

public class Controller {

    @FXML
    private Text myText;
    @FXML
    private TextField inputField;

    @FXML
    public void search() {
        Database.word = inputField.getText();
        String define = App.search(Database.word);
        myText.setText(define);
    }

    @FXML
    public void input() {
        System.out.println("Enter word: ");
        Scanner sc = new Scanner(System.in);
        Database.word = sc.nextLine();
    }
}