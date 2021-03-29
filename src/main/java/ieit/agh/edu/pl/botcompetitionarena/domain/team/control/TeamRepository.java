package ieit.agh.edu.pl.botcompetitionarena.domain.team.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
