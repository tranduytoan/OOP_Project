package com.tdt.dict.app.ui.controller;

import com.tdt.dict.app.core.game.hangman.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HangManController extends WindowController {
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView hangman;
    @FXML
    private ImageView lives;
    @FXML
    private Text secretWord;
    @FXML
    private Text suggestions, sg;
    @FXML
    private Button btnContinue;
    @FXML
    Text a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;

    private Game game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnContinue.setVisible(false);
        game = new Game();
        secretWord.setText(game.getSecretWord());
        updateImage();
        suggestions.setText(game.getWord().getWordExplain());
        suggestions.setVisible(false);
        sg.setVisible(false);
    }

    public void handleGame() {
        if (game.isLose()) {
            secretWord.setText("You lose! The word is " + game.getWord().getWordTarget());
            btnContinue.setVisible(true);
            return;
        } else if (game.isWin()) {
            secretWord.setText("You win! The word is " + game.getWord().getWordTarget());
            btnContinue.setVisible(true);
            return;
        }
        secretWord.setText(game.getSecretWord());
        if (game.getLives() == 3) {
            sg.setVisible(true);
            suggestions.setVisible(true);
        }
    }

    public void updateImage() {
        int liveCount = game.getLives();
        int tdt = 7 - liveCount;
        ImageView imageView1 = new ImageView(Objects.requireNonNull(getClass().getResource("/gameImage/hangman/" + tdt + ".png")).toString());
        ImageView imageView2 = new ImageView(Objects.requireNonNull(getClass().getResource("/gameImage/hangman/" + liveCount + "r.png")).toString());
        hangman.setImage(imageView1.getImage());
        lives.setImage(imageView2.getImage());
    }

    public void handleSelectChar(MouseEvent event) {
        if (game.isLose() || game.isWin()) {
            return;
        }
        Text text = (Text) event.getSource();
        Character letter = text.getText().toLowerCase().charAt(0);
        text.setVisible(false);

        game.play(letter);
        updateImage();
        handleGame();
    }

    public void handleContinue() {
        game = new Game();
        secretWord.setText(game.getSecretWord());
        updateImage();
        suggestions.setText(game.getWord().getWordExplain());
        suggestions.setVisible(false);
        sg.setVisible(false);
        btnContinue.setVisible(false);

        // reset all the buttons
        a.setVisible(true);
        b.setVisible(true);
        c.setVisible(true);
        d.setVisible(true);
        e.setVisible(true);
        f.setVisible(true);
        g.setVisible(true);
        h.setVisible(true);
        i.setVisible(true);
        j.setVisible(true);
        k.setVisible(true);
        l.setVisible(true);
        m.setVisible(true);
        n.setVisible(true);
        o.setVisible(true);
        p.setVisible(true);
        q.setVisible(true);
        r.setVisible(true);
        s.setVisible(true);
        t.setVisible(true);
        u.setVisible(true);
        v.setVisible(true);
        w.setVisible(true);
        x.setVisible(true);
        y.setVisible(true);
        z.setVisible(true);
    }
}
