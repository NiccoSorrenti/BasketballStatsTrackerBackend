package niccolosorrenti.basketballStatsTrackerBackend.controllers;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.response.UserResponseDTO;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserResponseDTO me(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}