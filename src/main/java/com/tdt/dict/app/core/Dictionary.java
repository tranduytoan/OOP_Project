package com.tdt.dict.app.core;

import java.util.ArrayList;

public abstract class Dictionary {
    /*List Func:
     * init (load data/ connect to database)
     * close
     * getAllWords ( for Trie, Cmd, etc...)
     * search
     * insert
     * edit
     * delete
     *
     *  */

    public abstract void init();

    public abstract void close();

    public abstract ArrayList<Word> getAllWords();

    public abstract String search(String word);

    public abstract void insert(Word word);

    public abstract void edit(Word word);

    public abstract void delete(Word word);
}
