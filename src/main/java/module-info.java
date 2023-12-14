module com.tdt.dict {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.media;
    requires jlayer;

    opens com.tdt.dict.app.ui.controller to javafx.fxml;
    exports com.tdt.dict;
    exports com.tdt.dict.app.core;
    exports com.tdt.dict.app.ui.controller;
    exports com.tdt.dict.game.hangman.controller;
    opens com.tdt.dict.game.hangman.controller to javafx.fxml;
}