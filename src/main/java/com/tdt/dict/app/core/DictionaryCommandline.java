package com.tdt.dict.app.core;

import com.tdt.dict.app.core.game.hangman.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline extends Dictionary {
    Database database;

    @Override
    public void init() {
        database = Database.getInstance();
        database.Connect();
    }

    @Override
    public void close() {
        database.Disconnect();
        System.out.println("Goodbye!");
    }

    @Override
    public ArrayList<Word> getAllWords() {
        return Database.getInstance().getAllWords();
    }

    @Override
    public String search(String word) {
        if (word.isEmpty()) {
            System.out.println("Word cannot be empty");
            return null;
        }
        Word wordObj = Database.getInstance().Search(word);
        if (wordObj == null) {
            System.out.println("Word does not exist");
            return null;
        }
        return wordObj.getWordExplain();
    }

    @Override
    public void insert(Word word) {
        if (word.getWordTarget().isEmpty()) {
            System.out.println("Word cannot be empty");
            return;
        }
        if (Database.getInstance().Search(word.getWordTarget()) != null) {
            System.out.println("Word already exists");
            return;
        }
        Database.getInstance().Insert(word);
        System.out.println("Word added");
    }

    @Override
    public void edit(Word word) {
        Scanner scanner = new Scanner(System.in);
        if (word == null) {
            System.out.println("Word does not exist for edit");
        } else {
            System.out.println("Enter new word explain: ");
            word.setWordExplain(scanner.nextLine());
            Database.getInstance().EditWord(word);
            System.out.println("Word updated");
        }
    }

    @Override
    public void delete(Word word) {
        if (word.getWordTarget().isEmpty()) {
            System.out.println("Word cannot be empty");
            return;
        }
        Word wordObj = Database.getInstance().Search(word.getWordTarget());
        if (wordObj == null) {
            System.out.println("Word does not exist");
            return;
        } else {
            System.out.println("Do you want to delete word: ");
            System.out.println("\t" + wordObj.getWordTarget() + " " + wordObj.getWordExplain());
            System.out.println("YES/NO(y/n): ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if (choice.equals("y") || choice.equals("Y") || choice.equals("yes") || choice.equals("YES")) {
                Database.getInstance().DeleteWord(word);
                System.out.println("Word deleted");
            } else {
                System.out.println("Word not deleted");
            }
        }
    }

    public int menuSelect() {
        System.out.println("Welcome to Dictionary!");
        System.out.println("0. Exit");
        System.out.println("1. Add");
        System.out.println("2. Remove");
        System.out.println("3. Update");
        System.out.println("4. Display");
        System.out.println("5. Search");
        System.out.println("6. Game");
        System.out.println("7. Export");
        System.out.print("Your choice: ");

        int choice;
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        return choice;
    }

    public static void main(String[] args) {
        DictionaryCommandline dictionayCommandline = new DictionaryCommandline();
        dictionayCommandline.init();
        int choice = dictionayCommandline.menuSelect();

        while (choice != 0) {
            switch (choice) {
                case 1:
                    System.out.println("Insert word:");
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter word target: ");
                    String wordTarget = scanner.nextLine();
                    System.out.println("Enter word explain: ");
                    String wordExplain = scanner.nextLine();
                    Word word = new Word(wordTarget, wordExplain);
                    dictionayCommandline.insert(word);
                    System.out.println("Enter any key to continue");
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Remove word:");
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Enter word target: ");
                    String wordTarget1 = scanner1.nextLine();
                    Word word1 = new Word(wordTarget1);
                    dictionayCommandline.delete(word1);
                    System.out.println("Enter any key to continue");
                    scanner1.nextLine();
                    break;
                case 3:
                    System.out.println("Edit word");
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Enter word target: ");
                    Word word2 = Database.getInstance().Search(scanner2.nextLine());
                    dictionayCommandline.edit(word2);
                    System.out.println("Enter any key to continue");
                    scanner2.nextLine();
                    break;
                case 4:
                    ArrayList<Word> words = dictionayCommandline.getAllWords();
                    for (Word word3 : words) {
                        System.out.println(word3.getWordTarget() + " " + word3.getWordExplain());
                    }
                    System.out.println("Enter any key to continue :");
                    Scanner scanner3 = new Scanner(System.in);
                    scanner3.nextLine();
                    break;
                case 5:
                    System.out.println("Search word");
                    Scanner scanner4 = new Scanner(System.in);
                    System.out.println("Enter word target: ");
                    Word word4 = Database.getInstance().Search(scanner4.nextLine());
                    String wordExplain1 = dictionayCommandline.search(word4.getWordTarget());
                    System.out.println("Word explain: " + wordExplain1);
                    System.out.println("Enter any key to continue");
                    scanner4.nextLine();
                    break;
                case 6:
                    dictionayCommandline.playhangmanGame();
                    System.out.println("Do you want to continue? (y/n)");
                    Scanner scanner6 = new Scanner(System.in);
                    String choice6 = scanner6.nextLine();
                    if (choice6.equals("y") || choice6.equals("Y") || choice6.equals("yes") || choice6.equals("YES")) {
                        dictionayCommandline.playhangmanGame();
                    }
                    break;
                case 7:
                    String path = "C:/Users/toant/Downloads/Dictionary.txt";
                    File file = new File(path);
                    try (PrintWriter writer = new PrintWriter(file)) {
                        ArrayList<Word> words1 = dictionayCommandline.getAllWords();
                        for (Word word5 : words1) {
                            writer.println(word5.getWordTarget() + " - " + word5.getWordExplain());
                        }
                        System.out.println("Export successfully!");
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Enter any key to continue");
                    Scanner scanner5 = new Scanner(System.in);
                    scanner5.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            dictionayCommandline.clearScreen();
            choice = dictionayCommandline.menuSelect();
        }
        dictionayCommandline.close();
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void playhangmanGame() {
        String secrectWord;
        Word word;
        Scanner scanner = new Scanner(System.in);

        Game game = new Game();
        secrectWord = game.getSecretWord();

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Welcome to Hangman Game!\n\n");
        System.out.print("Press Enter to continue");
        scanner.nextLine();

        while (game.getLives() > 0) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Secret word: " + game.getSecretWord());
            System.out.println("Guessed letters: " + game.getGuessedLetters());
            System.out.println("Lives remaining: " + game.getLives());
            System.out.print("Guess a letter: ");
            String guess = scanner.nextLine();
            if (guess.length() != 1) {
                System.out.println("Invalid input");
                continue;
            }
            if (game.getGuessedLetters().contains(guess.charAt(0))) {
                System.out.println("You have already guessed this letter");
                continue;
            }
            game.play(guess.charAt(0));
            if (game.isWin()) {
                System.out.println("You win!");
                System.out.println("The word is: " + game.getWord().getWordTarget());
                break;
            }
            if (game.isLose()) {
                System.out.println("You lose!");
                System.out.println("The word is: " + game.getWord().getWordTarget());
                break;
            }
        }
    }
}
