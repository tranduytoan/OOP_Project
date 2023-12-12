package com.tdt.dict.app.core;

import java.util.ArrayList;

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
    }

    @Override
    public ArrayList<Word> getAllWords() {
        return null;
    }

    @Override
    public String search(String word) {
        return null;
    }

    @Override
    public void insert(Word word) {

    }

    @Override
    public void edit(Word word) {

    }

    @Override
    public void delete(Word word) {

    }

    public static void main(String[] args) {
        DictionaryCommandline dictionayCommandline = new DictionaryCommandline();
        dictionayCommandline.init();

        // code here:
    }
}
