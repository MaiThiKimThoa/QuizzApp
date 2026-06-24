module com.mtkt.quizappv1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mtkt.quizappv1 to javafx.fxml;
    exports com.mtkt.quizappv1;
}
