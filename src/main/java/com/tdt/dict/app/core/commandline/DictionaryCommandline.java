package com.tdt.dict.app.core.commandline;

import com.tdt.dict.app.core.Database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    /** Xuất ra commandline */
    public void showAllWords() {
        super.sortWords(); // Sắp xếp
        super.printWord(); // Xuất
    }

    public void showContainWords() {
        super.searchCommandLine();
    }

    public void dictionaryBasic() throws FileNotFoundException {
        this.insertFromFile();
        this.showAllWords();
    }

    public void dictionarySearcher() throws FileNotFoundException {
        this.insertFromFile();
        this.showContainWords();
    }

    public void dictionaryAdvanced() throws IOException, InterruptedException {
        while (true) {
            System.out.print("\nWelcome to My Application!\n" +
                    "[0] Exit\n" +
                    "[1] Add\n" +
                    "[2] Remove\n" +
                    "[3] Update\n" +
                    "[4] Display\n" +
                    "[5] Lookup\n" +
                    "[6] Search\n" +
                    "[7] Game\n" +
                    "[8] Import from file\n" +
                    "[9] Export to file\n" +
                    "Your action: ");
            Scanner input = new Scanner(System.in);
            int n = input.nextInt();
            switch (n) {
                case 0:
                    return;
                case 1:
                    System.out.println("Insert word and means ?");
                    this.insert();
                    break;
                case 2:
                    System.out.println("Remove word ?");
                    this.delete();
                    break;
                case 3:
                    System.out.println("Update word's means to means ?");
                    this.update();
                    break;
                case 4:
                    System.out.println("The list words are:");
                    this.showAllWords();
                    break;
                case 5:
                    System.out.println("The word's means you find:");
                    this.dictionaryLookup();
                    break;
                case 6:
                    System.out.println("The chars you want to find ?");
                    this.showContainWords();
                    break;
                case 7:
                    // Game.
                    break;
                case 8:
                    this.insertFromFile();
                    break;
                case 9:
                    this.dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DictionaryCommandline Main = new DictionaryCommandline();
        Main.dictionaryAdvanced();
    }
}
