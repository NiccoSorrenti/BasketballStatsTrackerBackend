package niccolosorrenti.basketballStatsTrackerBackend.payloads.errors;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

        String message,

        LocalDateTime timestamp

) {
}