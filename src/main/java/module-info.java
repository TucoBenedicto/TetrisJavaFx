module com.example.tetris_tuto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tetris to javafx.fxml;
    exports com.example.tetris;
}