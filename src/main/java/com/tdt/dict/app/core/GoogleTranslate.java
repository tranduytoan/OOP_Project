package com.tdt.dict.app.core;

import javazoom.jl.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class GoogleTranslate {
    private static final String TTS_URL = "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob";
    private static final String TRANSLATE_URL = "https://script.google.com/macros/s/AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg/exec?q=%s&target=%s&source=%s";

    public enum lang {
        ENGLISH("en"),
        VIETNAMESE("vi");

        private final String value;

        lang(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void speak(String text, lang lang) {
        HttpURLConnection con = null;
        try {
            String urlStr = TTS_URL +
                    "&tl=" + lang.getValue() +
                    "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

            con = (HttpURLConnection) new URL(urlStr).openConnection();
            new Player(con.getInputStream()).play();
        } catch (Exception e) {
            System.err.println("Text to speech error!");
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static String translate(lang langFrom, lang langTo, String text) throws IOException {
        String urlStr = String.format(
                TRANSLATE_URL,
                URLEncoder.encode(text, StandardCharsets.UTF_8),
                langTo.getValue(),
                langFrom.getValue()
        );

        HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            return in.lines().collect(Collectors.joining("\n"));
        } finally {
            con.disconnect();
        }
    }
}