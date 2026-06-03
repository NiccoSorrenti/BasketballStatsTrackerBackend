package niccolosorrenti.basketballStatsTrackerBackend.payloads.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GameStatRequestDTO(

        @NotNull(message = "Points are required")
        @Min(value = 0, message = "Points cannot be negative")
        Integer points,

        @NotNull(message = "Assists are required")
        @Min(value = 0, message = "Assists cannot be negative")
        Integer assists,

        @NotNull(message = "Rebounds are required")
        @Min(value = 0, message = "Rebounds cannot be negative")
        Integer rebounds

) {
}