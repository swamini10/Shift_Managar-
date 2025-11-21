package demo.Shift_Managar.service;

import demo.Shift_Managar.dto.ShiftDTO;
import demo.Shift_Managar.model.Shift;
import demo.Shift_Managar.model.ShiftStatus;
import demo.Shift_Managar.model.User;
import demo.Shift_Managar.repository.ShiftRepository;
import demo.Shift_Managar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    // Create new shift
    public Shift createShift(Long employeeId, LocalDateTime startTime, LocalDateTime endTime, 
                             String shiftType, String description) throws Exception {
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new Exception("Employee not found"));
        
        if (startTime.isAfter(endTime)) {
            throw new Exception("Start time must be before end time");
        }
        
        Shift shift = new Shift(employee, startTime, endTime, shiftType);
        shift.setDescription(description);
        
        Shift savedShift = shiftRepository.save(shift);
        
        // Send notification
        notificationService.createNotification(employee, 
            "Shift Assigned", 
            "You have been assigned a new " + shiftType + " shift",
            demo.Shift_Managar.model.NotificationType.SHIFT_ASSIGNED);
        
        return savedShift;
    }
    
    // Get employee's shifts
    public List<ShiftDTO> getEmployeeShifts(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new Exception("Employee not found"));
        
        return shiftRepository.findByEmployeeIdOrderByStartTimeDesc(employeeId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get employee's upcoming shifts
    public List<ShiftDTO> getUpcomingShifts(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new Exception("Employee not found"));
        
        LocalDateTime now = LocalDateTime.now();
        return shiftRepository.findEmployeeShiftsBetweenDates(employee, now, now.plusMonths(3))
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get all scheduled shifts
    public List<ShiftDTO> getAllScheduledShifts() {
        return shiftRepository.findByStatus(ShiftStatus.SCHEDULED)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get shift by ID
    public Optional<Shift> getShiftById(Long id) {
        return shiftRepository.findById(id);
    }
    
    // Update shift
    public Shift updateShift(Long id, LocalDateTime startTime, LocalDateTime endTime, 
                             String shiftType, String description) throws Exception {
        Shift shift = shiftRepository.findById(id)
            .orElseThrow(() -> new Exception("Shift not found"));
        
        if (startTime != null && endTime != null && startTime.isAfter(endTime)) {
            throw new Exception("Start time must be before end time");
        }
        
        boolean changed = false;
        
        if (startTime != null && !shift.getStartTime().equals(startTime)) {
            shift.setStartTime(startTime);
            changed = true;
        }
        if (endTime != null && !shift.getEndTime().equals(endTime)) {
            shift.setEndTime(endTime);
            changed = true;
        }
        if (shiftType != null && !shift.getShiftType().equals(shiftType)) {
            shift.setShiftType(shiftType);
            changed = true;
        }
        if (description != null) {
            shift.setDescription(description);
        }
        
        Shift updatedShift = shiftRepository.save(shift);
        
        // Send notification if shift was changed
        if (changed) {
            notificationService.createNotification(shift.getEmployee(), 
                "Shift Changed", 
                "Your shift schedule has been updated",
                demo.Shift_Managar.model.NotificationType.SHIFT_CHANGED);
        }
        
        return updatedShift;
    }
    
    // Cancel shift
    public Shift cancelShift(Long id) throws Exception {
        Shift shift = shiftRepository.findById(id)
            .orElseThrow(() -> new Exception("Shift not found"));
        
        shift.setStatus(ShiftStatus.CANCELLED);
        Shift cancelledShift = shiftRepository.save(shift);
        
        // Send notification
        notificationService.createNotification(shift.getEmployee(), 
            "Shift Cancelled", 
            "Your " + shift.getShiftType() + " shift has been cancelled",
            demo.Shift_Managar.model.NotificationType.SHIFT_CANCELLED);
        
        return cancelledShift;
    }
    
    // Delete shift
    public void deleteShift(Long id) throws Exception {
        Shift shift = shiftRepository.findById(id)
            .orElseThrow(() -> new Exception("Shift not found"));
        shiftRepository.delete(shift);
    }
    
    // Convert Shift to ShiftDTO
    private ShiftDTO convertToDTO(Shift shift) {
        return new ShiftDTO(
            shift.getId(),
            shift.getEmployee().getId(),
            shift.getEmployee().getFullName(),
            shift.getStartTime(),
            shift.getEndTime(),
            shift.getShiftType(),
            shift.getDescription(),
            shift.getStatus()
        );
    }
}
