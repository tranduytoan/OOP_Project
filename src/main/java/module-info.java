module com.tdt.dict {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.tdt.dict.app.ui.controller to javafx.fxml;
    exports com.tdt.dict;
    exports com.tdt.dict.app.core;
    exports com.tdt.dict.app.ui.controller;
}