module com.example.ceng453termprojectgroup11_frontend {
    requires javafx.controls;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    requires lombok;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires unirest.java;

    opens com.group11.client to javafx.fxml;
    exports com.group11.client;

    opens com.group11.client.dao to com.fasterxml.jackson.databind;

}