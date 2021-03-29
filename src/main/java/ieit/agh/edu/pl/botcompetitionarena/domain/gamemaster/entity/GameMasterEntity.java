package ieit.agh.edu.pl.botcompetitionarena.domain.gamemaster.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "game_master")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
