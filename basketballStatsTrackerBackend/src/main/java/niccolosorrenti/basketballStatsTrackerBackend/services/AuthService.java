package niccolosorrenti.basketballStatsTrackerBackend.services;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.enums.Role;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.RegisterRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
}