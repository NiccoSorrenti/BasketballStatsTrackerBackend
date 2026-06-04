package niccolosorrenti.basketballStatsTrackerBackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.GameStat;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.GameStatRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.response.GameStatResponseDTO;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.response.GameStatsSummaryDTO;
import niccolosorrenti.basketballStatsTrackerBackend.services.GameStatService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameStatController {

    private final GameStatService gameStatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameStatResponseDTO create(
            @RequestBody @Valid GameStatRequestDTO payload,
            Authentication authentication
    ) {

        String email = authentication.getName();

        GameStat savedGame =
                gameStatService.create(payload, email);

        return new GameStatResponseDTO(
                savedGame.getId(),
                savedGame.getPoints(),
                savedGame.getAssists(),
                savedGame.getRebounds(),
                savedGame.getOpponentTeam(),
                savedGame.getResult()
        );
    }

    @GetMapping
    public List<GameStatResponseDTO> findMyGames(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return gameStatService.findAllByUser(email)
                .stream()
                .map(game -> new GameStatResponseDTO(
                        game.getId(),
                        game.getPoints(),
                        game.getAssists(),
                        game.getRebounds(),
                        game.getOpponentTeam(),
                        game.getResult()
                ))
                .toList();
    }

    @PutMapping("/{id}")
    public GameStatResponseDTO update(
            @PathVariable UUID id,
            @RequestBody @Valid GameStatRequestDTO payload,
            Authentication authentication
    ) {

        String email = authentication.getName();

        GameStat updatedGame =
                gameStatService.update(id, payload, email);

        return new GameStatResponseDTO(
                updatedGame.getId(),
                updatedGame.getPoints(),
                updatedGame.getAssists(),
                updatedGame.getRebounds(),
                updatedGame.getOpponentTeam(),
                updatedGame.getResult()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            Authentication authentication
    ) {

        String email = authentication.getName();

        gameStatService.delete(id, email);
    }

    @GetMapping("/summary")
    public GameStatsSummaryDTO getSummary(Authentication authentication) {
        String email = authentication.getName();

        return gameStatService.getSummary(email);
    }
}