package niccolosorrenti.basketballStatsTrackerBackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.RegisterRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.response.RegisterResponseDTO;
import niccolosorrenti.basketballStatsTrackerBackend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDTO register(
            @RequestBody @Valid RegisterRequestDTO payload
    ) {

        User savedUser = authService.register(payload);

        return new RegisterResponseDTO(savedUser.getId());
    }
}