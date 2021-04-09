package ieit.agh.edu.pl.botcompetitionarena.domain.game.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
    @Query("select new ieit.agh.edu.pl.botcompetitionarena.domain.game.entity.GameSummary(g.id, g.name, g.version) " +
            "from GameEntity g")
    List<GameSummary> findAllWithoutPayload();
}
