package ieit.agh.edu.pl.botcompetitionarena.domain.game.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @Transactional
    public List<GameEntity> getGamesWithQueues() {
        List<GameEntity> resultGames = new ArrayList<>();
        List<GameEntity> games = gameRepository.findAll();
        for (GameEntity game : games) {
            Set<QueueEntity> queues = new HashSet<>();
            game.getQueues().forEach(queue -> {
                queues.add(QueueEntity.builder()
                        .id(queue.getId())
                        .name(queue.getName())
                        .deadline(queue.getDeadline())
                        .build());
            });

            resultGames.add(GameEntity.builder()
                    .id(game.getId())
                    .name(game.getName())
                    .version(game.getVersion())
                    .description(game.getDescription())
                    .queues(queues)
                    .build());
        }

        return resultGames;
    }

}
