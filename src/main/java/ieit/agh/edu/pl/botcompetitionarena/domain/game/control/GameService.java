package ieit.agh.edu.pl.botcompetitionarena.domain.game.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameEntity storeGame(String name, String version, MultipartFile payload) throws IOException {
        GameEntity game = new GameEntity(name, version, payload.getBytes());

        gameRepository.save(game);
        return game;
    }

    public GameEntity getGame(Long id) {
        Optional<GameEntity> game = gameRepository.findById(id);
        if (game.isPresent()) return game.get();
        throw new IllegalStateException("Game with id " + id + " does not exist");
    }

    public Stream<GameEntity> getAllGames() {
        return gameRepository.findAll().stream();
    }
}
