package com.tdt.dict.app.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    // Thêm một từ vào Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
    }

    // Kiểm tra xem một từ có tồn tại trong Trie không
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEndOfWord;
    }

    // Kiểm tra xem có từ nào bắt đầu bằng một tiền tố nào đó không
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private TrieNode searchNode(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return null;
            }
            node = node.children.get(ch);
        }
        return node;
    }

    // Tìm tất cả các từ bắt đầu bằng một tiền tố nào đó
    public ArrayList<String> getWordsStartsWith(String prefix) {
        ArrayList<String> words = new ArrayList<>();
        TrieNode node = searchNode(prefix);
        if (node != null) {
            dfsGetWordsSubtree(node, prefix, words);
        }
        return words;
    }

    private void dfsGetWordsSubtree(TrieNode node, String word, ArrayList<String> words) {
        if (node.isEndOfWord) {
            words.add(word);
        }
        for (char ch : node.children.keySet()) {
            if (node.children.get(ch) != null) {
                dfsGetWordsSubtree(node.children.get(ch), word + ch, words);
            }
        }
    }

    private static class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }
}

