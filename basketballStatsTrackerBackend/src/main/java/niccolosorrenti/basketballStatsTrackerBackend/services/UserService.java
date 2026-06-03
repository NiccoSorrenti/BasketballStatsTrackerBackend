package niccolosorrenti.basketballStatsTrackerBackend.services;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}