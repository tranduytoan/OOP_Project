module com.tranduytoan.dict.mydictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.tranduytoan.dict.mydictionary to javafx.fxml;
    exports com.tranduytoan.dict.mydictionary;
}