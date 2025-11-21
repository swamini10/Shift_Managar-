package demo.Shift_Managar.service;

import demo.Shift_Managar.dto.NotificationDTO;
import demo.Shift_Managar.model.Notification;
import demo.Shift_Managar.model.NotificationType;
import demo.Shift_Managar.model.User;
import demo.Shift_Managar.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    // Create notification
    public Notification createNotification(User user, String title, String message, NotificationType type) {
        Notification notification = new Notification(user, title, message, type);
        return notificationRepository.save(notification);
    }
    
    // Get user notifications
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findAll()
            .stream()
            .filter(n -> n.getUser().getId().equals(userId))
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Get unread notifications
    public List<NotificationDTO> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // Count unread notifications
    public Long countUnreadNotifications(User user) {
        return notificationRepository.countByUserAndIsReadFalse(user);
    }
    
    // Mark as read
    public Notification markAsRead(Long notificationId) throws Exception {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new Exception("Notification not found"));
        
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }
    
    // Mark all as read
    public void markAllAsRead(User user) {
        List<Notification> unreadNotifications = notificationRepository.findByUserAndIsReadFalse(user);
        unreadNotifications.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }
    
    // Delete notification
    public void deleteNotification(Long id) throws Exception {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new Exception("Notification not found"));
        notificationRepository.delete(notification);
    }
    
    // Convert Notification to NotificationDTO
    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
            notification.getId(),
            notification.getTitle(),
            notification.getMessage(),
            notification.getType(),
            notification.getIsRead(),
            notification.getCreatedAt()
        );
    }
}
