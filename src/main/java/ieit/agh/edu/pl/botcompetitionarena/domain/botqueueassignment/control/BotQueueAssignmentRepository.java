package ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.botqueueassignment.entity.BotQueueAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotQueueAssignmentRepository extends JpaRepository<BotQueueAssignmentEntity, Long> {
    List<BotQueueAssignmentEntity> findByQueue(Integer queue_id);
}
