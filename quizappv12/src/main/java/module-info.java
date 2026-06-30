module com.mtkt.quizappv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mtkt.quizappv1 to javafx.fxml;
    exports com.mtkt.quizappv1;
     exports com.mtkt.pojo;
}
