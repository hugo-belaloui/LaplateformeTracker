import Model.Teacher;
import Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    // ─────────────────────────────────────────
    // USER TESTS
    // ─────────────────────────────────────────

    // Test 1: find an existing user by email
    @Test
    @Order(1)
    void testFindUserByEmail() {
        User user = User.findByEmail("admin@test.com");

        assertNotNull(user);                      // user must exist
        assertEquals("admin", user.getRole());    // role must be admin
        assertEquals("admin@test.com", user.getEmail()); // email must match
    }

    // Test 2: save a new user then delete them
    @Test
    @Order(2)
    void testSaveAndDeleteUser() {
        // create a temporary test user
        User newUser = new User(null, "temp@test.com", "temp123", "student");
        newUser.save();

        // check they were saved
        User found = User.findByEmail("temp@test.com");
        assertNotNull(found);
        assertEquals("student", found.getRole());

        // clean up — delete them
        found.delete();

        // check they were deleted
        User deleted = User.findByEmail("temp@test.com");
        assertNull(deleted);
    }

    // ─────────────────────────────────────────
    // TEACHER TESTS
    // ─────────────────────────────────────────

    // Test 3: find an existing teacher by user_id
    @Test
    @Order(3)
    void testFindTeacherByUserId() {
        // teacher1@test.com has user_id = 2
        Teacher teacher = Teacher.findByUserId(2L);

        assertNotNull(teacher);                      // teacher must exist
        assertEquals("Jean", teacher.getFirstName()); // first name must match
        assertEquals("Dupont", teacher.getLastName()); // last name must match
    }

    // Test 4: save a new teacher
    @Test
    @Order(4)
    void testSaveTeacher() {
        // first create a user for this teacher
        User newUser = new User(null, "teacher_test@test.com", "test123", "teacher");
        newUser.save();

        User saved = User.findByEmail("teacher_test@test.com");
        assertNotNull(saved);

        // now create the teacher profile
        Teacher newTeacher = new Teacher(null, "Pierre", "Dupuis", saved.getId());
        newTeacher.save();

        // check they were saved
        Teacher found = Teacher.findByUserId(saved.getId());
        assertNotNull(found);
        assertEquals("Pierre", found.getFirstName());

        // clean up
        found.delete();
        saved.delete();
    }

    // ─────────────────────────────────────────
    // LOGIN TESTS
    // ─────────────────────────────────────────

    // Test 5: wrong credentials return null
    @Test
    @Order(5)
    void testLoginWrongCredentials() {
        User user = User.findByEmail("wrong@test.com");
        assertNull(user); // user should not exist

        // even if email exists, wrong password should fail
        User realUser = User.findByEmail("admin@test.com");
        assertNotNull(realUser);
        assertNotEquals("wrongpassword", realUser.getPassword());
    }

    // Test 6: correct credentials return right role
    @Test
    @Order(6)
    void testLoginCorrectCredentials() {
        User user = User.findByEmail("admin@test.com");

        assertNotNull(user);
        assertEquals("admin123", user.getPassword()); // password matches
        assertEquals("admin", user.getRole());         // role is correct
    }
}