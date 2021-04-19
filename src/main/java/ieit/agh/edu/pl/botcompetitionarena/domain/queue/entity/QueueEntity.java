package ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.library.entity.LibraryEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "queue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime deadline;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] config;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] log;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToMany(mappedBy = "queues")
    private Set<LibraryEntity> libraries = new HashSet<>();

    @OneToMany(mappedBy = "queue")
    private Set<BotQueueAssignmentEntity> bots;

    public QueueEntity(String name, LocalDateTime deadline, byte[] config, GameEntity game) {
        this.name = name;
        this.deadline = deadline;
        this.config = config;
        this.game = game;
    }

    public QueueEntity(String name, LocalDateTime deadline, byte[] config, byte[] log, GameEntity game) {
        this.name = name;
        this.deadline = deadline;
        this.config = config;
        this.log = log;
        this.game = game;
    }

    @JsonIgnore
    public byte[] getConfig() {
        return config;
    }

    @JsonIgnore
    public GameEntity getGame() {
        return game;
    }
}
