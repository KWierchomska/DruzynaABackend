package ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bot_queue_assignment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotQueueAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer place;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] log;

    @ManyToOne
    @JoinColumn(name = "bot_id")
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private QueueEntity queue;

    public BotQueueAssignmentEntity(Integer place) {
        this.place = place;
    }

    public BotQueueAssignmentEntity(Integer place, byte[] log) {
        this.place = place;
        this.log = log;
    }

    public BotQueueAssignmentEntity(BotEntity bot, QueueEntity queue) {
        this.bot = bot;
        this.queue = queue;
    }
}
