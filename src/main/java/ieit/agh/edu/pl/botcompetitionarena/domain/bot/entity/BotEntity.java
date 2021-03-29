package ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bot")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
