module com.example.tcpmulticlient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.tcpmulticlient to javafx.fxml;
    opens com.example.tcpmulticlient.TCP to javafx.fxml;
    exports com.example.tcpmulticlient.TCP.Tasca2;
    opens com.example.tcpmulticlient.TCP.Tasca2 to javafx.fxml;
}