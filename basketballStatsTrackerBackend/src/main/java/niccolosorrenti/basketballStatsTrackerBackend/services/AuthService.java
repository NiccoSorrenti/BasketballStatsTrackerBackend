package niccolosorrenti.basketballStatsTrackerBackend.services;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.enums.Role;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.LoginRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.RegisterRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.UserRepository;
import niccolosorrenti.basketballStatsTrackerBackend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User register(RegisterRequestDTO payload) {

        if (userRepository.existsByEmail(payload.email())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(payload.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(payload.username())
                .email(payload.email())
                .password(passwordEncoder.encode(payload.password()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public String login(LoginRequestDTO payload) {

        User user = userRepository.findByEmail(payload.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(payload.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(
                Map.of(),
                user.getEmail()
        );
    }
}