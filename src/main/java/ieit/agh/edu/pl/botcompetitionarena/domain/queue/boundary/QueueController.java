package ieit.agh.edu.pl.botcompetitionarena.domain.queue.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.StatusLogsFileService;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueService;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

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

    @GetMapping(value = "queue/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getQueueStatus() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(StatusLogsFileService.readLastLog());
        return ResponseEntity.ok().body(json);
    }

}
