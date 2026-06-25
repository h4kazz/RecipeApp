package lt.techin.exam.auth.dto;

import lt.techin.exam.user.model.UserRole;

public record AuthResponse(
        String username,
        String email,
        UserRole role
) {
}
