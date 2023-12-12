package com.tdt.dict.app.core.game.hangman;

import com.tdt.dict.app.core.Database;
import com.tdt.dict.app.core.Word;

import java.util.List;
import java.util.Random;

public class Logic {
    public Word getRandomWord() {
        List<Word> allWords = Database.getInstance().getAllWords();
        if (allWords.isEmpty()) {
            return null; // No words in the database
        }
        Random random = new Random();
        while (true) {
            int index = random.nextInt(allWords.size());
            Word word = allWords.get(index);
            if (!word.getWordTarget().contains(" ")) {
                word.setWordTarget(word.getWordTarget().toLowerCase());
                return word;
            }
        }
    }

    public String getSecretWord(Word word) {
        return "-".repeat(word.getWordTarget().length());
    }

    public boolean checkLetter(char letter, Word word) {
        return word.getWordTarget().contains(String.valueOf(letter));
    }

    public String updateSecretWord(char letter, Word word, String secretWord) {
        StringBuilder newSecretWord = new StringBuilder(secretWord);
        for (int i = 0; i < word.getWordTarget().length(); i++) {
            if (word.getWordTarget().charAt(i) == letter) {
                newSecretWord.setCharAt(i, letter);
            }
        }
        return newSecretWord.toString();
    }

    public boolean checkWin(String secretWord) {
        return !secretWord.contains("-");
    }
}
