package demo.Shift_Managar.repository;

import demo.Shift_Managar.model.Shift;
import demo.Shift_Managar.model.ShiftStatus;
import demo.Shift_Managar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByEmployee(User employee);
    
    List<Shift> findByEmployeeAndStatus(User employee, ShiftStatus status);
    
    List<Shift> findByStatus(ShiftStatus status);
    
    @Query("SELECT s FROM Shift s WHERE s.employee = :employee AND s.startTime >= :startDate AND s.startTime <= :endDate")
    List<Shift> findEmployeeShiftsBetweenDates(@Param("employee") User employee, 
                                                @Param("startDate") LocalDateTime startDate, 
                                                @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT s FROM Shift s WHERE s.startTime >= :startDate AND s.startTime <= :endDate ORDER BY s.startTime")
    List<Shift> findAllShiftsBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    List<Shift> findByEmployeeIdOrderByStartTimeDesc(Long employeeId);
}
