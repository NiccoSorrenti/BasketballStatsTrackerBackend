package niccolosorrenti.basketballStatsTrackerBackend.payloads.requests;

import jakarta.validation.constraints.Min;

public record GameStatRequestDTO(

        @Min(value = 0)
        Integer points,

        @Min(value = 0)
        Integer assists,

        @Min(value = 0)
        Integer rebounds

) {
}