package niccolosorrenti.basketballStatsTrackerBackend.entities;

import jakarta.persistence.*;
import lombok.*;
import niccolosorrenti.basketballStatsTrackerBackend.enums.GameResult;

import java.util.UUID;

@Entity
@Table(name = "game_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStat {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Integer assists;

    @Column(nullable = false)
    private Integer rebounds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String opponentTeam;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameResult result;
}