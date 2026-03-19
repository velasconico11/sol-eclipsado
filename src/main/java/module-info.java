module org.example.soleclipsado {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.unsupported.desktop;


    opens org.example.soleclipsado to javafx.fxml;
    exports org.example.soleclipsado;
    exports org.example.soleclipsado.controller;
    opens org.example.soleclipsado.controller to javafx.fxml;
}