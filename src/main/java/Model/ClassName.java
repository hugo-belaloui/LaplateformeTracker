package Model;
import utils.DatabaseConnection;
import java.util.ArrayList;

public class ClassName {
    private String name;
    private ArrayList<Student> students;

    public ClassName(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public String getName() { return name; }
    public ArrayList<Student> getStudents() { return students; }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
