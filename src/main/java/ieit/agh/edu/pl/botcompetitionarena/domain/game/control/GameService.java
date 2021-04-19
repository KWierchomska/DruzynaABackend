package ieit.agh.edu.pl.botcompetitionarena.domain.game.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity storeGame(String name, String version, String description, MultipartFile payload)
            throws IOException {
        GameEntity game = new GameEntity(name, version, description, payload.getBytes());

        gameRepository.save(game);
        return game;
    }

    public GameEntity getGame(Long id) {
        Optional<GameEntity> game = gameRepository.findById(id);
        if (game.isPresent()) return game.get();
        throw new IllegalStateException("Game with id " + id + " does not exist");
    }

    public List<GameSummary> getAllWithoutPayload() {
        return gameRepository.findAllWithoutPayload();
    }
}
