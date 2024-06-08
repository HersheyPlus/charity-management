package helper;

public class StringHelper {
    public static String generateId(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    public static String generateId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
