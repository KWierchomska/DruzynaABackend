package ieit.agh.edu.pl.botcompetitionarena.domain.game.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "game")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
