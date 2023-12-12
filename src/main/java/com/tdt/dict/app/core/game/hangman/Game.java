package com.tdt.dict.app.core.game.hangman;

import com.tdt.dict.app.core.Word;

import java.util.ArrayList;

public class Game {
    private Word word;
    private String secretWord;
    private int lives;
    private int Length;
    private ArrayList<Character> guessedLetters;
    private boolean isWin;
    private boolean isLose;
    Logic logic;

    public Game() {
        logic = new Logic();
        word = logic.getRandomWord();
        secretWord = logic.getSecretWord(word);
        lives = 6;
        Length = word.getWordTarget().length();
        guessedLetters = new ArrayList<>();
        isWin = false;
        isLose = false;
    }

    public Word getWord() {
        return word;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getLives() {
        return lives;
    }

    public int getLength() {
        return Length;
    }

    public ArrayList<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void play(Character letter) {
        if (guessedLetters.contains(letter)) {
            return;
        }
        if (logic.checkLetter(letter, word)) {
            secretWord = logic.updateSecretWord(letter, word, secretWord);
        } else {
            lives--;
        }
        guessedLetters.add(letter);
        isWin = logic.checkWin(secretWord);
        isLose = lives == 0;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isLose() {
        return isLose;
    }
}
