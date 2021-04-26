package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.control.BotQueueAssignmentService;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueService;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BotService {
    private final BotRepository botRepository;
    private final QueueService queueService;
    private final BotQueueAssignmentService botQueueAssignmentService;

    @Autowired
    public BotService(BotRepository botRepository, QueueService queueService,
                      BotQueueAssignmentService botQueueAssignmentService) {
        this.botRepository = botRepository;
        this.queueService = queueService;
        this.botQueueAssignmentService = botQueueAssignmentService;
    }

    public BotEntity storeBot(String name, String version, Long queueId, MultipartFile payload) throws IOException {
        BotEntity bot = new BotEntity(name, version, payload.getBytes());

        botRepository.save(bot);
        QueueEntity queue = queueService.getQueue(queueId);
        botQueueAssignmentService.storeBotQueueAssignment(bot, queue);
        return bot;
    }

    public BotEntity getBot(Long id) {
        Optional<BotEntity> bot = botRepository.findById(id);
        if (bot.isPresent()) return bot.get();
        throw new IllegalStateException("Bot with id " + id + " does not exist");
    }

    public List<BotEntity> getBotsByQueueId(Long queueId) {
        return botRepository.getBotEntitiesById(queueId);
    }

}
