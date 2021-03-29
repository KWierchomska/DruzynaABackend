package ieit.agh.edu.pl.botcompetitionarena.domain.match.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.match.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
