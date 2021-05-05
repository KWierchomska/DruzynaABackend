package ieit.agh.edu.pl.botcompetitionarena.domain.queue.boundary;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.GubpProjectRunner;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueService;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class QueueController {
    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/start-game")
    public ResponseEntity<Object> uploadGame(@RequestParam("gameId") Long gameId,
                                             @RequestParam("name") String name,
                                             @RequestParam("config") MultipartFile config,
                                             @RequestParam("deadline")
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     LocalDateTime deadline) {
        try {
            QueueEntity queue = queueService.addQueue(name, gameId, deadline, config);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(queue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to start game");
        }
    }

    @GetMapping(path = "/queue-log/{id}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<Object> getQueueLog(@PathVariable Long id) {
        QueueEntity queue = queueService.getQueue(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + queue.getName() + " Log " + ".zip\"")
                .body(queue.getLog());
    }

    @Transactional
    @GetMapping("/run-queue/{queue-id}")
    public ResponseEntity<List<String>> runQueue(@PathVariable("queue-id") Long queueId) throws IOException {
        QueueEntity queue = queueService.getQueue(queueId);
        GameEntity game = queue.getGame();
        return ResponseEntity.ok()
                .body(GubpProjectRunner.run(queue, game));
    }
}
