package com.tdt.dict.app.core;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    private static Database instance;
    private Connection conn;

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = null;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Kết nối đến db
     */
    public void Connect() {
        if (conn != null) {
            return;
        }
        try {
            URL url1 = getClass().getProtectionDomain().getCodeSource().getLocation();
            File jarFile = new File(url1.toURI());
            File parentDirectory = jarFile.getParentFile();
            String parentDirpath = parentDirectory.getAbsolutePath().replaceAll("\\\\", "/");
            System.out.println("Thư mục chứa tệp JAR: " + parentDirpath);

//            String url = "jdbc:sqlite::resource:database/dictionary.db";
//            String url = "jdbc:sqlite:" + parentDirpath + "/classes/database/dictionary.db";
            String url = "jdbc:sqlite:" + parentDirpath + "/classes/database/dictionary.db";
//            String url = "jdbc:sqlite:"
//                    + Objects.requireNonNull(
//                    getClass().getResource(
//                            "/database/dictionary.db")).getPath();
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ngắt kết nối đến db
     */
    public void Disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Word Search(String word) {
        StringBuilder description = new StringBuilder();
        StringBuilder pronounce = new StringBuilder();
        StringBuilder html = new StringBuilder();
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return null;
        }
        String sql = "SELECT * FROM av WHERE word = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word);

            //execute query
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Not found");
                return null;
            } else {
                do {
                    html.append(rs.getString("html"));
                    description.append(rs.getString("description"));
                    pronounce.append(rs.getString("pronounce"));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Search successfully");
        return new Word(word, html.toString(), description.toString(), pronounce.toString());
    }

    public void Insert(Word word) {
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return;
        }
        String sql = "INSERT INTO av(word, html, description, pronounce) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word.getWordTarget());
            stmt.setString(2, word.getWordHtml());
            stmt.setString(3, word.getWordExplain());
            stmt.setString(4, word.getWordPronounce());

            //execute query
            int rs = stmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Insert successfully");
            } else {
                System.out.println("Insert failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteWord(Word word) {
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return;
        }
        String sql = "DELETE FROM av WHERE word = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word.getWordTarget());

            //execute query
            int rs = stmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Delete successfully");
            } else {
                System.out.println("Delete failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void EditWord(Word word) {
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return;
        }
        String sql = "UPDATE av SET html = ?, description = ?, pronounce = ? WHERE word = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word.getWordHtml());
            stmt.setString(2, word.getWordExplain());
            stmt.setString(3, word.getWordPronounce());
            stmt.setString(4, word.getWordTarget());

            //execute query
            int rs = stmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Edit successfully");
            } else {
                System.out.println("Edit failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> words = new ArrayList<>();
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return words;
        }
        String sql = "SELECT * FROM av";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //execute query
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Not found");
                return words;
            } else {
                do {
                    String word = rs.getString("word");
                    String description = rs.getString("description");
                    words.add(new Word(word, description));
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return words;
    }

    public static void main(String[] args) {
        Database main = new Database();
        main.Connect();
        String word;
        Scanner sc = new Scanner(System.in);
        boolean search = true;
        boolean insert = false;
        label:
        while (true) {
            System.out.print("Enter word: ");
            word = sc.nextLine();
            switch (word) {
                case "/exit":
                    main.Disconnect();
                    break label;
                case "/insert":
                    System.out.println("Insert mode");
                    search = false;
                    insert = true;
                    continue;
                case "/search":
                    System.out.println("Search mode");
                    search = true;
                    insert = false;
                    continue;
                default:
                    if (insert) {
                        if (word.isEmpty()) {
                            System.out.println("Word cannot be empty");
                            continue;
                        }
                        System.out.print("Enter html: ");
                        String html = sc.nextLine();
                        if (html.isEmpty()) {
                            html = "null";
                        }
                        System.out.print("Enter description: ");
                        String description = sc.nextLine();
                        if (description.isEmpty()) {
                            description = "this word has no description";
                        }
                        System.out.print("Enter pronounce: ");
                        String pronounce = sc.nextLine();
                        if (pronounce.isEmpty()) {
                            pronounce = "null";
                        }
                        main.Insert(new Word(word, html, description, pronounce));
                    } else if (search) {
                        main.Search(word);
                    }
            }

        }
    }
}
