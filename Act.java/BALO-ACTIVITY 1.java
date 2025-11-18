import java.util.Random;
import java.util.Scanner;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

class PermissionManager {

    public String getPermissions(User user) {
        switch (user.getRole()) {
            case "student":
                return "VIEW ONLY";
            case "teacher":
                return "VIEW + EDIT";
            case "admin":
                return "FULL ACCESS";
            default:
                return "NO PERMISSION";
        }
    }
}

class SecurityLayer {
     
    public boolean passwordCheck(String password) {
        return password.equals("olab2");  // changed password
    }

    public int generate2FACode() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    public boolean twoFactorCheck(int realCode, int userCode) {
        return realCode == userCode;
    }

    public boolean intrusionDetection(boolean otpMatch) {
        return otpMatch;
    }
}

public class Act {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = input.nextLine();

        // Only allows specific user
        if (!username.equalsIgnoreCase("cams")) {   // changed username
            System.out.println("Access denied. Unknown user.");
            return;
        }

        System.out.print("Enter role (student, teacher, admin): ");
        String role = input.nextLine();

        User user = new User(username, role);

        PermissionManager pm = new PermissionManager();
        SecurityLayer sl = new SecurityLayer();

        System.out.println("Logging in as: " + user.getUsername());

        System.out.print("Enter password: ");
        String password = input.nextLine();
        if (!sl.passwordCheck(password)) {
            System.out.println("Password failed.");
            return;
        }

        int otp = sl.generate2FACode();
        System.out.println("Your 2FA code: " + otp);

        System.out.print("Enter the 2FA code: ");
        int typedOtp = input.nextInt();
        input.nextLine();

        boolean otpMatch = sl.twoFactorCheck(otp, typedOtp);

        if (!sl.intrusionDetection(otpMatch)) {
            System.out.println("Suspicious activity detected! Access blocked.");
            return;
        }

        System.out.println("Login successful!");
        System.out.println("Permissions granted: " + pm.getPermissions(user));
    }
}