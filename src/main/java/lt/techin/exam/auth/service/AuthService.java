
package lt.techin.exam.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lt.techin.exam.auth.dto.AuthResponse;
import lt.techin.exam.auth.dto.LoginRequest;
import lt.techin.exam.auth.dto.RegisterRequest;
import lt.techin.exam.exception.customexception.EmailDuplicateException;
import lt.techin.exam.exception.customexception.UsernameDuplicateException;
import lt.techin.exam.user.model.User;
import lt.techin.exam.user.repository.UserRepository;
import lt.techin.exam.user.model.UserRole;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest req, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        writeAccessCookie(response, jwtService.generateToken(user));
        return new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public AuthResponse register(RegisterRequest req, HttpServletResponse response) {
        if (userRepository.existsByUsername(req.username())) {
            throw new UsernameDuplicateException(req.username());
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new EmailDuplicateException(req.email());
        }

        User user = User.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .email(req.email())
                .role(UserRole.USER)
                .build();
        userRepository.save(user);

        writeAccessCookie(response, jwtService.generateToken(user));
        return new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public AuthResponse getMe(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameDuplicateException(username));
        return new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    private void writeAccessCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMillis(jwtService.getExpirationMs()))
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}