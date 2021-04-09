package ieit.agh.edu.pl.botcompetitionarena.domain.game.boundary;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.control.GameService;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary;
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

import java.util.List;
import java.util.NoSuchElementException;


@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/upload-game")
    public ResponseEntity<Object> uploadGame(@RequestParam("name") String name,
                                             @RequestParam("version") String version,
                                             @RequestParam("payload") MultipartFile payload) {
        try {
            GameEntity game = gameService.storeGame(name, version, payload);
            System.out.println("GAME " + game.getId() + " UPLOADED");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GameSummary(game.getId(), game.getName(), game.getVersion()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload game");
        }
    }

    @GetMapping(path = "/game/{id}", produces = "application/hal+json;charset=utf8")
    public ResponseEntity<Object> getGame(@PathVariable Long id) {
        try {
            GameEntity game = gameService.getGame(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + game.getName() + ".zip\"")
                    .body(game.getPayload());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Game with id " + id + " doesn't exist");
        }
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameSummary>> getGames() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllWithoutPayload());
    }
}
