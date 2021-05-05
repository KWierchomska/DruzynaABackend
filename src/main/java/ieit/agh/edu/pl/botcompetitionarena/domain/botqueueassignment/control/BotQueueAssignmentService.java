package ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BotQueueAssignmentService {
    private final BotQueueAssignmentRepository botQueueAssignmentRepository;

    @Autowired
    public BotQueueAssignmentService(BotQueueAssignmentRepository botQueueAssignmentRepository) {
        this.botQueueAssignmentRepository = botQueueAssignmentRepository;
    }

    public List<BotQueueAssignmentEntity> getBotsPlacementForQueue(Integer queueId) {
        return botQueueAssignmentRepository.findByQueue(queueId);
    }

    public BotQueueAssignmentEntity storeBotQueueAssignment(BotEntity bot, QueueEntity queue) {
        BotQueueAssignmentEntity assignment = new BotQueueAssignmentEntity(bot, queue);

        botQueueAssignmentRepository.save(assignment);
        return assignment;
    }

    public BotQueueAssignmentEntity getBotQueueAssignment(Long id) {
        Optional<BotQueueAssignmentEntity> assignment = botQueueAssignmentRepository.findById(id);
        if (assignment.isPresent()) return assignment.get();
        throw new IllegalStateException("Assignment bot to queue with id " + id + " does not exist");
    }
}
