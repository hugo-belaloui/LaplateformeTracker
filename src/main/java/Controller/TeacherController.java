package Controller;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import Model.ClassName;
import Model.Student;
import Utils.StageManager;
import Utils.SessionManager;

public class TeacherController {

    @FXML private ListView<String> classListView;
    @FXML private ListView<String> studentListView;
    @FXML private Button addStudentButton;
    @FXML private Button delStudentButton;
    @FXML private Text firstNameText;
    @FXML private Text lastNameText;
    @FXML private Text ageText;
    @FXML private Text avgGradesText;

    private ClassName selectedClass = null;
    private ArrayList<ClassName> classes = new ArrayList<>();

    private void loadFromDatabase() {
        ClassName allStudents = new ClassName(1L, "All Students"); // ✅ fixed

        for (Student s : Student.findAll()) {
            allStudents.addStudent(s);
        }
        classes.add(allStudents);
    }

    private void loadClassPanel() {
        ArrayList<String> classNames = new ArrayList<>();
        for (ClassName c : classes) {
            classNames.add(c.getName());
        }
        classListView.setItems(FXCollections.observableArrayList(classNames));
    }

    private void loadStudentPanel(ClassName c) {
        ArrayList<String> studentNames = new ArrayList<>();
        for (Student s : c.getStudents()) {
            studentNames.add(s.getFirstName() + " " + s.getLastName());
        }
        studentListView.setItems(FXCollections.observableArrayList(studentNames));
    }

    private void showStudentDetails(String fullName) {
        for (Student s : selectedClass.getStudents()) {
            if ((s.getFirstName() + " " + s.getLastName()).equals(fullName)) {
                firstNameText.setText(s.getFirstName());
                lastNameText.setText(s.getLastName());
                ageText.setText(String.valueOf(s.getAge()));

                ArrayList<Double> grades = s.getGrades();
                if (!grades.isEmpty()) {
                    double sum = 0;
                    for (double grade : grades) {
                        sum += grade;
                    }
                    avgGradesText.setText(String.format("%.2f", sum / grades.size()));
                }
                return;
            }
        }
    }

    @FXML
    public void handleAddStudent() {
        if (selectedClass == null) return;

        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");
        dialog.setHeaderText("Add a student to " + selectedClass.getName());

        ButtonType addButton = new ButtonType("Add");
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstNameField = new TextField();
        TextField lastNameField  = new TextField();
        TextField ageField       = new TextField();

        grid.add(new Label("First name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last name:"),  0, 1);
        grid.add(lastNameField,  1, 1);
        grid.add(new Label("Age:"),        0, 2);
        grid.add(ageField,       1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonClicked -> {
            if (buttonClicked == addButton) {
                try {
                    String firstName = firstNameField.getText().trim();
                    String lastName  = lastNameField.getText().trim();
                    byte age         = Byte.parseByte(ageField.getText().trim());
                    return new Student(null, firstName, lastName, age, null);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(student -> {
            student.save();                    // saves to database ✅
            selectedClass.addStudent(student); // adds to current view
            loadStudentPanel(selectedClass);   // refreshes the list
        });
    }

    @FXML
    public void handleDeleteStudent() {
        if (selectedClass == null) return;

        String selected = studentListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // find student and delete from database ✅
        for (Student s : selectedClass.getStudents()) {
            if ((s.getFirstName() + " " + s.getLastName()).equals(selected)) {
                s.delete();
                break;
            }
        }

        // remove from memory
        selectedClass.getStudents().removeIf(s ->
            (s.getFirstName() + " " + s.getLastName()).equals(selected)
        );

        // refresh list and clear details
        loadStudentPanel(selectedClass);
        firstNameText.setText("-");
        lastNameText.setText("-");
        ageText.setText("-");
        avgGradesText.setText("-");
    }

    @FXML
    public void handleLogout() { // ✅ added
        SessionManager.clear();
        StageManager.switchScene("/View/Login.fxml");
    }

    @FXML
    public void initialize() {
        loadFromDatabase();
        loadClassPanel();

        classListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    for (ClassName c : classes) {
                        if (c.getName().equals(newValue)) {
                            selectedClass = c;
                            loadStudentPanel(c);
                            break;
                        }
                    }
                }
            });

        studentListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null && selectedClass != null) {
                    showStudentDetails(newValue);
                }
            });
    }
}