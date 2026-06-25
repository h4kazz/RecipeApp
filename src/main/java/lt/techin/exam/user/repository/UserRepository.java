package lt.techin.exam.user.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lt.techin.exam.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}