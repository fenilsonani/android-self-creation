package com.example.display_product;

public class LeaveRequest {
    private int id;
    private int employeeId;
    private String reason;
    private String status;
    private String date;
    private String time;

    public LeaveRequest() {
        // Default constructor
    }

    public LeaveRequest(int employeeId, String reason, String status, String date, String time) {
        this.employeeId = employeeId;
        this.reason = reason;
        this.status = status;
        this.date = date;
        this.time = time;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
