package com.tdt.dict.app.core;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection conn;
    public static String word = "";

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
            String url = "jdbc:sqlite:" + getClass().getResource("/database/dictionary.db").toString();
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

    public String Search(String word) {
        StringBuilder result = new StringBuilder();
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return "Connection to SQLite has been failed.";
        }
        String sql = "SELECT description FROM av WHERE word = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word);

            //execute query
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Not found");
                return "Not found";
            } else {
                do {
                    String description = rs.getString("description");
                    System.out.println(description);
                    result.append(description).append("\n");
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }

    public void Insert(String word, String html, String description, String pronounce) {
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return;
        }
        String sql = "INSERT INTO av(word, html, description, pronounce) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, word);
            stmt.setString(2, html);
            stmt.setString(3, description);
            stmt.setString(4, pronounce);

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

    public void Commit() {
        this.Connect();
        if (conn == null) {
            System.out.println("Connection to SQLite has been failed.");
            return;
        }

        try {
            conn.commit();
            System.out.println("Commit successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                case "/commit":
                    main.Commit();
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
                        main.Insert(word, html, description, pronounce);
                    } else if (search) {
                        main.Search(word);
                    }
            }

        }
    }
}
