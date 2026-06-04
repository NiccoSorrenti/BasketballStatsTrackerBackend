package niccolosorrenti.basketballStatsTrackerBackend.payloads.response;

public record GameStatsSummaryDTO(
        int totalGames,
        double avgPoints,
        double avgAssists,
        double avgRebounds,
        int totalPoints,
        int totalAssists,
        int totalRebounds,
        int careerHighPoints,
        int careerHighAssists,
        int careerHighRebounds,
        String playerType
) {
}