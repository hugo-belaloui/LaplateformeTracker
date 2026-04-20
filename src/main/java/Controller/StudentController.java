package Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import Model.Student;
import Model.User;
import Utils.SessionManager;
import Utils.StageManager;
import java.util.ArrayList;

public class StudentController {

    @FXML private ListView<String> gradesListView;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label ageLabel;
    @FXML private Label avgGradeLabel;

    @FXML
    public void initialize() {
        User user = User.findByEmail(SessionManager.getEmail());
        System.out.println("Email from session: " + SessionManager.getEmail());

        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        System.out.println("User found: " + user.getId());

        Student student = Student.findByUserId(user.getId());

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("Student found: " + student.getFirstName());

        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        ageLabel.setText(String.valueOf(student.getAge()));

        ArrayList<Double> grades = student.getGrades();
        if (!grades.isEmpty()) {
            double sum = 0;
            ArrayList<String> gradeStrings = new ArrayList<>();
            for (double grade : grades) {
                sum += grade;
                gradeStrings.add(String.valueOf(grade));
            }
            gradesListView.setItems(FXCollections.observableArrayList(gradeStrings));
            avgGradeLabel.setText(String.format("%.2f", sum / grades.size()));
        } else {
            System.out.println("No grades found!");
        }
    }

    @FXML
    public void handleLogout() {
        SessionManager.clear();
        StageManager.switchScene("/View/Login.fxml");
    }
}