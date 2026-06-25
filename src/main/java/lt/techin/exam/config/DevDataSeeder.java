package lt.techin.exam.config;

import lt.techin.exam.user.model.User;
import lt.techin.exam.user.model.UserRole;
import lt.techin.exam.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DevDataSeeder {
    @Bean
    CommandLineRunner seedDevUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createUserIfMissing(userRepository, passwordEncoder, "admin", "amdin@admin.com", "password", UserRole.ADMIN);
        };
    }

    private static void createUserIfMissing(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String email,
            String password,
            UserRole role
    ) {
        if (userRepository.existsByEmail(email)) {
            return;
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);
    }

}
