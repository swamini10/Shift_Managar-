package demo.Shift_Managar.controller;

import demo.Shift_Managar.dto.ShiftDTO;
import demo.Shift_Managar.model.Shift;
import demo.Shift_Managar.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shifts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShiftController {
    
    @Autowired
    private ShiftService shiftService;
    
    @PostMapping
    public ResponseEntity<?> createShift(
            @RequestParam Long employeeId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime,
            @RequestParam String shiftType,
            @RequestParam(required = false) String description) {
        try {
            Shift shift = shiftService.createShift(employeeId, startTime, endTime, shiftType, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Shift created successfully",
                "shiftId", shift.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeShifts(@PathVariable Long employeeId) {
        try {
            List<ShiftDTO> shifts = shiftService.getEmployeeShifts(employeeId);
            return ResponseEntity.ok(shifts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/employee/{employeeId}/upcoming")
    public ResponseEntity<?> getUpcomingShifts(@PathVariable Long employeeId) {
        try {
            List<ShiftDTO> shifts = shiftService.getUpcomingShifts(employeeId);
            return ResponseEntity.ok(shifts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/all-scheduled")
    public ResponseEntity<?> getAllScheduledShifts() {
        try {
            List<ShiftDTO> shifts = shiftService.getAllScheduledShifts();
            return ResponseEntity.ok(shifts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable Long id) {
        try {
            Optional<Shift> shift = shiftService.getShiftById(id);
            if (shift.isPresent()) {
                return ResponseEntity.ok(shift.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Shift not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateShift(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam(required = false) String shiftType,
            @RequestParam(required = false) String description) {
        try {
            Shift updatedShift = shiftService.updateShift(id, startTime, endTime, shiftType, description);
            return ResponseEntity.ok(Map.of(
                "message", "Shift updated successfully",
                "shiftId", updatedShift.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelShift(@PathVariable Long id) {
        try {
            Shift cancelledShift = shiftService.cancelShift(id);
            return ResponseEntity.ok(Map.of(
                "message", "Shift cancelled successfully",
                "shiftId", cancelledShift.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable Long id) {
        try {
            shiftService.deleteShift(id);
            return ResponseEntity.ok(Map.of("message", "Shift deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
