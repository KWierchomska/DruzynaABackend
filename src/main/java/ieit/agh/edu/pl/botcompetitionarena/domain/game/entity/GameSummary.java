package ieit.agh.edu.pl.botcompetitionarena.domain.game.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameSummary {
    private Long id;
    private String name;
    private String version;
}
