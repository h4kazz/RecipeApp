package lt.techin.exam.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String path = request.getRequestURI();
        String message = path.contains("/login")
                ? "Invalid username or password"
                : "Authentication required or token expired";

        response.getWriter().write("""
            {
                "status": 401,
                "error": "UNAUTHORIZED",
                "message": "%s"
            }
            """.formatted(message));
    }
}