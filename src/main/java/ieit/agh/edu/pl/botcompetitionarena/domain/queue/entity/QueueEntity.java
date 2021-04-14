package ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue")
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

    @ManyToOne
    @JoinColumn(name = "gameId")
    private GameEntity game;

    public QueueEntity(String name, LocalDateTime deadline, byte[] config, GameEntity game) {
        this.name = name;
        this.deadline = deadline;
        this.config = config;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
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
