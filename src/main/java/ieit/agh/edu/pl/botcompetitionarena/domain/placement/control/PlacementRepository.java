package ieit.agh.edu.pl.botcompetitionarena.domain.placement.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.placement.entity.PlacementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementRepository extends JpaRepository<PlacementEntity, Long> {
}
