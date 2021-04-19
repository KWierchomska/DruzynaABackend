package ieit.agh.edu.pl.botcompetitionarena.domain.queue.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.control.GameService;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {
    private final QueueRepository queueRepository;
    private final GameService gameService;

    @Autowired
    public QueueService(QueueRepository queueRepository, GameService gameService) {
        this.queueRepository = queueRepository;
        this.gameService = gameService;
    }

    public QueueEntity addQueue(String name, Long gameId, LocalDateTime deadline, MultipartFile config)
            throws IOException {
        GameEntity game = gameService.getGame(gameId); //TODO get only id not the whole game
        QueueEntity queue = new QueueEntity(name, deadline, config.getBytes(), game);
        queueRepository.save(queue);

        return queue;
    }

    public QueueEntity getQueue(Long id){
        return queueRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Queue with id " + id + " does not exist"));
    }
}
