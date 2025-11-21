package demo.Shift_Managar.dto;

import demo.Shift_Managar.model.ShiftStatus;
import java.time.LocalDateTime;

public class ShiftDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String shiftType;
    private String description;
    private ShiftStatus status;

    // Constructors
    public ShiftDTO() {}

    public ShiftDTO(Long id, Long employeeId, String employeeName, LocalDateTime startTime, 
                   LocalDateTime endTime, String shiftType, String description, ShiftStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShiftStatus getStatus() {
        return status;
    }

    public void setStatus(ShiftStatus status) {
        this.status = status;
    }
}
