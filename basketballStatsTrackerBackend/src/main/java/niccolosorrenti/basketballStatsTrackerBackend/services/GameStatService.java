package niccolosorrenti.basketballStatsTrackerBackend.services;

import lombok.RequiredArgsConstructor;
import niccolosorrenti.basketballStatsTrackerBackend.entities.GameStat;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import niccolosorrenti.basketballStatsTrackerBackend.payloads.requests.GameStatRequestDTO;
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
            throw new RuntimeException("Unauthorized");
        }

        gameStat.setPoints(payload.points());
        gameStat.setAssists(payload.assists());
        gameStat.setRebounds(payload.rebounds());

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
}