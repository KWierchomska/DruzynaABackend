package ieit.agh.edu.pl.botcompetitionarena.domain.vote.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.vote.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
}
