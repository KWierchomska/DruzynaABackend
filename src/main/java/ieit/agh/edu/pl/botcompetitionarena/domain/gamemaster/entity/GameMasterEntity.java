package ieit.agh.edu.pl.botcompetitionarena.domain.gamemaster.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    private String name;
    private String surname;
    private String email;
    private String password;

    @OneToMany(mappedBy = "gameMaster")
    private Set<GameEntity> games;

    public GameMasterEntity(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
