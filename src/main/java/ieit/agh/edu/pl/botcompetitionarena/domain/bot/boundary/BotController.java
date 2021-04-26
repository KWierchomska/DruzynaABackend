package ieit.agh.edu.pl.botcompetitionarena.domain.bot.boundary;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.BotService;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.ConfigFileCreator;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.control.GubpProjectRunner;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotSummary;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class BotController {
    private final BotService botService;

    @Autowired
    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping("/upload-bot")
    public ResponseEntity<Object> uploadBot(@RequestParam("name") String name,
                                             @RequestParam("version") String version,
                                             @RequestParam("payload") MultipartFile payload) {
        try {
            BotEntity bot = botService.storeBot(name, version, payload);
            System.out.println("BOT " + bot.getId() + " UPLOADED");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BotSummary(bot.getId(), bot.getName(), bot.getVersion()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload bot");
        }
    }

    @GetMapping(path = "/bot/{id}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<Object> getBot(@PathVariable Long id) {
        try {
            BotEntity bot = botService.getBot(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + bot.getName() + ".zip\"")
                    .body(bot.getPayload());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Bot with id " + id + " doesn't exist");
        }
    }
}
