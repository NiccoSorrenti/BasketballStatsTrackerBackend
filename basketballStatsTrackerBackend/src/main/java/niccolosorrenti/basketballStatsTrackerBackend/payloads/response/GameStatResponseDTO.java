package niccolosorrenti.basketballStatsTrackerBackend.payloads.response;

import niccolosorrenti.basketballStatsTrackerBackend.enums.GameResult;

import java.util.UUID;

public record GameStatResponseDTO(
        UUID id,
        Integer points,
        Integer assists,
        Integer rebounds,
        String opponentTeam,
        GameResult result
) {
}