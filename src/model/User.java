package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String role;
    private String username;
    private String password;
// "admin" or "user"

    public User(int id, String name, String email, String role,String username,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setUsername(String username) {
       this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + role + ")";
    }
}
