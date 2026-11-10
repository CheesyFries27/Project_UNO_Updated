import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User {

    private static final SecureRandom RANDOM = new SecureRandom();

    private int id;
    private String username;        
    private String email;           
    private String passwordHash;    

    public User(String username, String password, String email) {
        setUsername(username);
        setPassword(password);
        this.email = email;
    }

    // It's true if the typed password matches the saved one
    public boolean checkPassword(String attempt) {
        if (attempt == null) {
            return false;
        }
        String[] parts = passwordHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expected = Base64.getDecoder().decode(parts[1]);
        return MessageDigest.isEqual(expected, digest(salt, attempt));
    }

    private static byte[] digest(byte[] salt, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        this.username = username.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }

    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        this.passwordHash = Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(digest(salt, password));
    }
}