package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BotService {

    private final BotRepository botRepository;

    @Autowired
    public BotService(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    public BotEntity storeBot(String name, String version, MultipartFile payload) throws IOException {
        BotEntity bot = new BotEntity(name, version, payload.getBytes());

        botRepository.save(bot);
        return bot;
    }

    public BotEntity getBot(Long id) {
        Optional<BotEntity> bot = botRepository.findById(id);
        if (bot.isPresent()) return bot.get();
        throw new IllegalStateException("Bot with id " + id + " does not exist");
    }

    public List<BotEntity> getBotsByQueueId(Long queueId) {
        return botRepository.getBotEntitiesById(queueId);
    }

}
