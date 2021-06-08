package ieit.agh.edu.pl.botcompetitionarena.domain.game.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.gamemaster.entity.GameMasterEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    private String name;
    private String version;
    private String description;

    private String controllerRelativePath;
    private String configRelativePath;
    private String gameRelativePath;
    private String resultRelativePath;
    private String runCommand;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] payload;

    @OneToMany(mappedBy = "game")
    private Set<QueueEntity> queues;

    @ManyToOne
    @JoinColumn(name = "game_master_id", referencedColumnName = "id")
    private GameMasterEntity gameMaster;

    public GameEntity(String name, String version, String description, byte[] payload) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.payload = payload;
    }

    public GameEntity(String name, String version, String description, byte[] payload,
                      String controllerRelativePath, String configRelativePath,
                      String gameRelativePath, String resultRelativePath, String runCommand) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.payload = payload;
        this.controllerRelativePath = controllerRelativePath;
        this.configRelativePath = configRelativePath;
        this.gameRelativePath = gameRelativePath;
        this.resultRelativePath = resultRelativePath;
        this.runCommand = runCommand;
    }
}
