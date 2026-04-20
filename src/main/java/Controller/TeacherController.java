package controller;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.ClassName;
import model.Student;
import utils.StageManager;
import utils.SessionManager;

public class TeacherController {
    @FXML
    private ListView<String> classListView;
    @FXML
    private ListView<String> studentListView;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button delStudentButton;
    @FXML
    private ListView<String> gradesListView;
    @FXML
    private Button addGradeButton;
    @FXML
    private Button delGradeButton;
    @FXML
    private Button editGradeButton;
    @FXML
    private Text firstNameText;
    @FXML
    private Text lastNameText;
    @FXML
    private Text ageText;
    @FXML
    private Text avgGradesText;

    private ClassName selectedClass = null;
    private Student selectedStudent = null;

    private ArrayList<ClassName> classes = new ArrayList<>();

    private void loadFromDatabase() {
        ClassName allStudents = new ClassName(1L, "All Students");

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

    private void loadGradesPanel(Student s) {
        ArrayList<String> gradeStrings = new ArrayList<>();
        for (double grade : s.getGrades()) {
            gradeStrings.add(String.format("%.2f", grade));
        }
        gradesListView.setItems(FXCollections.observableArrayList(gradeStrings));
    }

    private void showStudentDetails(String fullName) {
        for (Student s : selectedClass.getStudents()) {
            if ((s.getFirstName() + " " + s.getLastName()).equals(fullName)) {
                selectedStudent = s;
                selectedStudent.reloadGrades();

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
                } else {
                    avgGradesText.setText("-");
                }

                loadGradesPanel(s);
                return;
            }
        }
    }

    @FXML
    private void handleAddGrade() {
        if (selectedStudent == null) {
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Grade");
        dialog.setHeaderText("Add a grade for " + selectedStudent.getFirstName());
        dialog.setContentText("Grade:");

        /* Optional<String> bcs thats what dialog.showAndWait() returns */
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            try {
                double grade = Double.parseDouble(value.trim());
                selectedStudent.addGrade(grade);
                selectedStudent.updateGrades();
                loadGradesPanel(selectedStudent);
                showStudentDetails(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
            }
            catch (NumberFormatException e) {
                System.err.println("Error made when adding grade.");
            }
        });
    }

    @FXML
    private void handleDeleteGrade() {
        if (selectedStudent == null) {
            return;
        }

        int index = gradesListView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }

        selectedStudent.getGrades().remove(index);
        selectedStudent.updateGrades();
        showStudentDetails(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
    }

    @FXML
    private void handleEditGrade() {
        if (selectedStudent == null) {
            return;
        }

        int index = gradesListView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }

        double currentGrade = selectedStudent.getGrades().get(index);

        TextInputDialog dialog = new TextInputDialog(String.format("%.2f", currentGrade));
        dialog.setTitle("Edit Grade");
        dialog.setHeaderText("Edit grade for " + selectedStudent.getFirstName());
        dialog.setContentText("New value:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            try {
                double newGrade = Double.parseDouble(value.trim());
                selectedStudent.getGrades().set(index, newGrade);
                selectedStudent.updateGrades();
                showStudentDetails(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
            } 
            catch (NumberFormatException e) {
            }
        });
    }

    
    @FXML
    private void handleAddStudent() {
        if (selectedClass == null) 
        {
            return;}

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
            student.save();
            selectedClass.addStudent(student);
            loadStudentPanel(selectedClass);
        });
    }

    @FXML
    public void handleDeleteStudent() {
        if (selectedClass == null) return;

        String selected = studentListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Student toDelete = null;
        for (Student s : selectedClass.getStudents()) {
            if ((s.getFirstName() + " " + s.getLastName()).equals(selected)) {
                toDelete = s;
                break;
            }
        }
        if (toDelete == null) return;
        toDelete.delete();
        selectedClass.getStudents().remove(toDelete);

        selectedClass.getStudents().removeIf(s ->
            (s.getFirstName() + " " + s.getLastName()).equals(selected)
        );


        loadStudentPanel(selectedClass);
        firstNameText.setText("-");
        lastNameText.setText("-");
        ageText.setText("-");
        avgGradesText.setText("-");
    }

    @FXML
    public void handleLogout() {
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
