package ieit.agh.edu.pl.botcompetitionarena.domain.gamemaster.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.gamemaster.entity.GameMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMasterRepository extends JpaRepository<GameMasterEntity, Long> {
}
