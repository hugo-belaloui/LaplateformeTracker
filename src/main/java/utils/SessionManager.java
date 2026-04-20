package Utils;

public class SessionManager {
    private static String email;
    private static String role;

    public static void setEmail(String e) { email = e; }
    public static void setRole(String r)  { role = r; }

    public static String getEmail() { return email; }
    public static String getRole()  { return role; }

    public static void clear() {
        email = null;
        role = null;
    }
}