package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import Utils.StageManager;
import Utils.SessionManager;

public class StudentController {

    @FXML private ListView<String> gradesListView;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label ageLabel;
    @FXML private Label avgGradeLabel;

    @FXML
    public void initialize() {
        // we will load student info from DB here later
    }

    @FXML
    public void handleLogout() {
        SessionManager.clear();
        StageManager.switchScene("/View/Login.fxml");
    }
}