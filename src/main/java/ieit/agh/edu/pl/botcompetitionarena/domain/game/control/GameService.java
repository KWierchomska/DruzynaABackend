package ieit.agh.edu.pl.botcompetitionarena.domain.game.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public GameEntity storeGame(String name, String version, String description, MultipartFile payload,
                                String controllerRelativePath, String configRelativePath,
                                String gameRelativePath, String resultRelativePath)
            throws IOException {
        GameEntity game = new GameEntity(name, version, description, payload.getBytes(),
                controllerRelativePath, configRelativePath, gameRelativePath, resultRelativePath);

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

    public GameEntity getGameWithQueues(Long id) {
        GameEntity game = getGame(id);
        Set<QueueEntity> queues = new HashSet<>();
        game.getQueues().forEach(queue -> {
            queues.add(QueueEntity.builder()
                    .id(queue.getId())
                    .name(queue.getName())
                    .deadline(queue.getDeadline())
                    .build());
        });
        return GameEntity.builder()
                .id(game.getId())
                .name(game.getName())
                .version(game.getVersion())
                .description(game.getDescription())
                .queues(queues)
                .build();
    }

}
