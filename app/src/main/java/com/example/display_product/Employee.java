package com.example.display_product;

public class Employee {
    private int id;
    private String username;
    private String password;

    public Employee() {
        // Default constructor
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
