package ieit.agh.edu.pl.botcompetitionarena.domain.game.boundary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameResponse {
    private Long id;
    private String name;
    private String version;
}
