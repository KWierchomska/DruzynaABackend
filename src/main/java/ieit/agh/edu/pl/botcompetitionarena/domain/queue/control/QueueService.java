package ieit.agh.edu.pl.botcompetitionarena.domain.queue.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.control.GameService;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final GameService gameService;

    @Autowired
    public QueueService(QueueRepository queueRepository, GameService gameService) {
        this.queueRepository = queueRepository;
        this.gameService = gameService;
    }

    public QueueEntity addQueue(String name, Long gameId, LocalDateTime deadline, MultipartFile config)
            throws IOException {
        GameEntity game = gameService.getGame(gameId);
        QueueEntity queue = new QueueEntity(name, deadline, config.getBytes(), game);
        queueRepository.save(queue);

        return queue;
    }

    @Transactional
    public QueueEntity getQueue(Long id) {
        return queueRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Queue with id " + id + " does not exist"));
    }

    public QueueEntity getQueueWithBots(Long id) {
        QueueEntity queue = getQueue(id);
        Set<BotQueueAssignmentEntity> botQueueAssignments = new HashSet<>();
        queue.getBots().forEach(botQueueAssignment -> {
            BotEntity bot = botQueueAssignment.getBot();
            BotEntity newBot = (BotEntity.builder()
                    .id(bot.getId())
                    .name(bot.getName())
                    .version(bot.getVersion())
                    .build());
            botQueueAssignments.add(BotQueueAssignmentEntity.builder()
                    .id(botQueueAssignment.getId())
                    .bot(newBot)
                    .build());
        });
        return QueueEntity.builder()
                .id(queue.getId())
                .name(queue.getName())
                .deadline(queue.getDeadline())
                .lastStatus(queue.getLastStatus())
                .bots(botQueueAssignments)
                .build();
    }
}
