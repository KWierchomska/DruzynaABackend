package ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.boundary;

import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.control.BotQueueAssignmentService;
import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class BotQueueAssignmentController {
    private BotQueueAssignmentService queueAssignmentService;

    @Autowired
    public BotQueueAssignmentController(BotQueueAssignmentService queueAssignmentService){
        this.queueAssignmentService = queueAssignmentService;
    }

    @GetMapping(path = "/queue-placement/{queueId}")
    public ResponseEntity<Object> getBotPlacementForQueue(@PathVariable Integer queueId) {
        try {
            List<BotQueueAssignmentEntity> botsPlacementForQueue = queueAssignmentService.getBotsPlacementForQueue(queueId);
            return ResponseEntity.status(HttpStatus.OK).body(botsPlacementForQueue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There is no queue with id: " + queueId);
        }
    }
}
