package ieit.agh.edu.pl.botcompetitionarena.domain.teammember.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.teammember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Long> {
}
