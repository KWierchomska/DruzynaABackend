package ieit.agh.edu.pl.botcompetitionarena;

import ieit.agh.edu.pl.botcompetitionarena.domain.queue.boundary.QueueController;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueRepository;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class RunQueues {
    private final QueueRepository queueRepository;
    private final QueueController queueController;

    @Autowired
    public RunQueues(QueueRepository queueRepository, QueueController queueController) {
        this.queueRepository = queueRepository;
        this.queueController = queueController;
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void run() throws IOException {
        LocalDate date = LocalDate.now();
        List<QueueEntity> queueList = queueRepository.findAll();
        for (QueueEntity queue: queueList) {
            if (queue.getLastStatus() == null) {
                LocalDate queueDate = LocalDate.of(queue.getDeadline().getYear(), queue.getDeadline().getMonth(), queue.getDeadline().getDayOfMonth());
                if (queueDate.isBefore(date)) {
                    queueController.runQueue(queue.getId());
                }
            }
        }
    }
}
