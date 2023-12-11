package com.tdt.dict.app.core;

public class Word {
    private String word_target;
    private String word_explain;
    private String word_html;
    private String word_pronounce;

    public Word() {
    }

    public Word(String word_target) {
        this.word_target = word_target;
    }

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(String word_target, String word_html, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.word_html = word_html;
    }

    public Word(String word_target, String word_html, String word_explain, String word_pronounce) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.word_html = word_html;
        this.word_pronounce = word_pronounce;
    }

    public String getWordTarget() {
        return word_target;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWordHtml() {
        return word_html;
    }

    public void setWordHtml(String word_html) {
        this.word_html = word_html;
    }

    public String getWordPronounce() {
        return word_pronounce;
    }

    public void setWordPronounce(String word_pronounce) {
        this.word_pronounce = word_pronounce;
    }

    @Override
    public String toString() {
        return word_target + "\t" + word_explain;
    }
}
