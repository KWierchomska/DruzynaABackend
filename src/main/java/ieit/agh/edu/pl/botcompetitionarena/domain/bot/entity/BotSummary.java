package ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BotSummary {
    private Long id;
    private String name;
    private String version;
}
