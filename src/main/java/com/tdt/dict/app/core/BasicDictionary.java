package com.tdt.dict.app.core;

import java.util.ArrayList;

public class BasicDictionary extends Dictionary {
    public static BasicDictionary instance;
    private Trie trie;
    private Database database;

    private BasicDictionary() {
        database = Database.getInstance();
        init();
    }

    public static BasicDictionary getInstance() {
        if (instance == null) {
            instance = new BasicDictionary();
        }
        return instance;
    }

    @Override
    public void init() {
        trie = new Trie();
        ArrayList<Word> words = getAllWords();
        for (Word word : words) {
            trie.insert(word.getWordTarget());
        }

        // log
        System.out.println("trie initialized");
        System.out.println("BasicDictionary initialized");
    }

    @Override
    public void close() {
        database.Disconnect();
        trie = null;
    }

    @Override
    public ArrayList<Word> getAllWords() {
        return database.getAllWords();
    }

    @Override
    public String search(String word) {
        Word wordObj = database.Search(word);
        if (wordObj == null) {
            return "Not found";
        }
        return wordObj.getWordHtml();
    }

    @Override
    public void insert(Word word) {
        database.Insert(word);
        trie.insert(word.getWordTarget());
    }

    @Override
    public void edit(Word word) {
        database.EditWord(word);
    }

    @Override
    public void delete(Word word) {
        database.DeleteWord(word);
        trie.deleteWord(word.getWordTarget());
    }

    public Trie getTrie() {
        return trie;
    }
}
