package ieit.agh.edu.pl.botcompetitionarena.domain.game.boundary;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.control.GameService;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;


@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/upload-game")
    public ResponseEntity<String> uploadGame(@RequestParam("name") String name,
                                             @RequestParam("version") String version,
                                             @RequestParam("payload") MultipartFile payload) {
        String message = "";
        try {
            GameEntity game = gameService.storeGame(name, version, payload);
            System.out.println("GAME " + game.getId() +" UPLOADED");

            message = "Game uploaded successfully";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Failed to upload game";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping(path="/game/{id}", produces="application/hal+json;charset=utf8")
    public ResponseEntity<Object> getGame(@PathVariable Long id) {
        try {
            GameEntity game = gameService.getGame(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + game.getName() + ".zip\"")
                    .body(game.getPayload());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error fetching game");
        }
    }

}
