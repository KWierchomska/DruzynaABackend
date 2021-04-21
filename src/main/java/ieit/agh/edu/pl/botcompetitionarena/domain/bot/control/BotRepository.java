package ieit.agh.edu.pl.botcompetitionarena.domain.bot.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotRepository extends JpaRepository<BotEntity, Long> {

    @Query("select q.bots from QueueEntity q " +
            "where :queue_id = q.id")
    List<BotEntity> getBotEntitiesById(@Param("queue_id") Long queueId);

}
