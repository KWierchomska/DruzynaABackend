package ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

import ieit.agh.edu.pl.botcompetitionarena.domain.team.entity.TeamEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;

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
    private String name;
    private String version;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] payload;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToMany(mappedBy = "bot")
    private Set<BotQueueAssignmentEntity> queues;

    public BotEntity(String name, String version, byte[] payload) {
        this.name = name;
        this.version = version;
        this.payload = payload;
    }
}
