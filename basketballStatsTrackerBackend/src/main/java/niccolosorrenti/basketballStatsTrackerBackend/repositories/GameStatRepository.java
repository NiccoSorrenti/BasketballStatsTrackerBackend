package niccolosorrenti.basketballStatsTrackerBackend.repositories;

import niccolosorrenti.basketballStatsTrackerBackend.entities.GameStat;
import niccolosorrenti.basketballStatsTrackerBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameStatRepository extends JpaRepository<GameStat, UUID> {

    List<GameStat> findByUser(User user);
}