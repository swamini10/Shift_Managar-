package demo.Shift_Managar.repository;

import demo.Shift_Managar.model.User;
import demo.Shift_Managar.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(UserRole role);
    
    List<User> findByActive(Boolean active);
    
    Boolean existsByEmail(String email);
}
