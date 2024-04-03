package filrouge.groupei.boredealsappandroid;

public class HelperClass {

    private String name, email, username;
    // Removed password, as it's handled by Firebase Authentication

    // Constructors
    public HelperClass() {
        // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
    }

    public HelperClass(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
