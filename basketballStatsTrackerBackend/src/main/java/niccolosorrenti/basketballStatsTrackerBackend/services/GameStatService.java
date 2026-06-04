package niccolosorrenti.basketballStatsTrackerBackend.services;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.GameStat;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.exceptions.NotFoundException;
import niccolosorrenti.basketballStatsTrackerBackend.exceptions.UnauthorizedException;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.GameStatRequestDTO;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.response.GameStatsSummaryDTO;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.GameStatRepository;
import niccolosorrenti.basketballStatsTrackerBackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameStatService {

    private final GameStatRepository gameStatRepository;
    private final UserRepository userRepository;

    public GameStat create(GameStatRequestDTO payload, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GameStat gameStat = GameStat.builder()
                .points(payload.points())
                .assists(payload.assists())
                .rebounds(payload.rebounds())
                .opponentTeam(payload.opponentTeam())
                .result(payload.result())
                .user(user)
                .build();

        return gameStatRepository.save(gameStat);
    }

    public List<GameStat> findAllByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return gameStatRepository.findByUser(user);
    }

    public GameStat update(
            UUID gameId,
            GameStatRequestDTO payload,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GameStat gameStat = gameStatRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (!gameStat.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(
                    "You are not allowed to modify this game"
            );
        }

        gameStat.setPoints(payload.points());
        gameStat.setAssists(payload.assists());
        gameStat.setRebounds(payload.rebounds());
        gameStat.setOpponentTeam(payload.opponentTeam());
        gameStat.setResult(payload.result());

        return gameStatRepository.save(gameStat);
    }

    public void delete(UUID gameId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GameStat gameStat = gameStatRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (!gameStat.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        gameStatRepository.delete(gameStat);
    }

    public GameStatsSummaryDTO getSummary(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<GameStat> games = gameStatRepository.findByUser(user);

        int totalGames = games.size();

        if (totalGames == 0) {
            return new GameStatsSummaryDTO(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "Rookie");
        }

        double avgPoints = games.stream()
                .mapToInt(GameStat::getPoints)
                .average()
                .orElse(0);

        double avgAssists = games.stream()
                .mapToInt(GameStat::getAssists)
                .average()
                .orElse(0);

        double avgRebounds = games.stream()
                .mapToInt(GameStat::getRebounds)
                .average()
                .orElse(0);

        int totalPoints = games.stream()
                .mapToInt(GameStat::getPoints)
                .sum();

        int totalAssists = games.stream()
                .mapToInt(GameStat::getAssists)
                .sum();

        int totalRebounds = games.stream()
                .mapToInt(GameStat::getRebounds)
                .sum();

        int careerHighPoints = games.stream()
                .mapToInt(GameStat::getPoints)
                .max()
                .orElse(0);

        int careerHighAssists = games.stream()
                .mapToInt(GameStat::getAssists)
                .max()
                .orElse(0);

        int careerHighRebounds = games.stream()
                .mapToInt(GameStat::getRebounds)
                .max()
                .orElse(0);

        String playerType;

        if (avgPoints >= 20 && avgAssists >= 7 && avgRebounds >= 7) {
            playerType = "All Around Player";
        } else if (avgPoints >= 20) {
            playerType = "Scorer";
        } else if (avgAssists >= 7) {
            playerType = "Playmaker";
        } else if (avgRebounds >= 10) {
            playerType = "Rebounder";
        } else {
            playerType = "Role Player";
        }

        return new GameStatsSummaryDTO(
                totalGames,
                Math.round(avgPoints * 10.0) / 10.0,
                Math.round(avgAssists * 10.0) / 10.0,
                Math.round(avgRebounds * 10.0) / 10.0,
                totalPoints,
                totalAssists,
                totalRebounds,
                careerHighPoints,
                careerHighAssists,
                careerHighRebounds,
                playerType
        );
    }
}