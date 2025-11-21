package demo.Shift_Managar.repository;

import demo.Shift_Managar.model.Notification;
import demo.Shift_Managar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
    
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    List<Notification> findByUserAndIsReadFalse(User user);
    
    Long countByUserAndIsReadFalse(User user);
}
