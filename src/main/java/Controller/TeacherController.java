package Controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import Model.ClassName;
import Model.Student;

public class TeacherController {
    @FXML
    private ListView<String> classListView;
    @FXML
    private ListView<String> studentListView;
    @FXML
    private Text firstNameText;
    @FXML
    private Text lastNameText;
    @FXML
    private Text ageText;
    @FXML
    private Text avgGradesText;

    private ClassName selectedClass = null;

    private ArrayList<ClassName> classes = new ArrayList<>();

    private void loadTestData() {
        Student achraf = new Student(1L, "Achraf", "xxxxxxx", (byte) 20, 1L);
        Student hugo = new Student(2L, "Hugo", "xxxxxxx", (byte) 21, 2L);
        Student andre = new Student(3L, "André", "xxxxx", (byte) 22, 3L);

        achraf.addGrades(new double[]{19.0, 20.0});
        hugo.addGrades(new double[]{19.0, 20.0});
        andre.addGrades(new double[]{19.0, 20.0});

        ClassName class5th = new ClassName("5eme");
        class5th.addStudent(achraf);
        class5th.addStudent(hugo);

        ClassName class5th2 = new ClassName("5eme2");
        class5th2.addStudent(andre);

        classes.add(class5th);
        classes.add(class5th2);
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
        double sum = 0;
        double avg = 0;
        ArrayList<Double> grades;

        for (Student s : selectedClass.getStudents()) {
            if ((s.getFirstName() + " " + s.getLastName()).equals(fullName)) {
                firstNameText.setText(s.getFirstName());
                lastNameText.setText(s.getLastName());
                ageText.setText(String.valueOf(s.getAge()));

                grades = s.getGrades();
                if (!grades.isEmpty()) {
                    avg = sum / grades.size();
                    avgGradesText.setText(String.format("%.2f", avg));
                }
                return ;
            }
        }
    }

    @FXML
    public void initialize() {
        loadTestData();
        loadClassPanel();

        classListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

        studentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && selectedClass != null) {
                showStudentDetails(newValue);
            }
        });
    }
}
