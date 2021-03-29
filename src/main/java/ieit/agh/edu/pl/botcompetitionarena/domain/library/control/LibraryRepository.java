package ieit.agh.edu.pl.botcompetitionarena.domain.library.control;

import ieit.agh.edu.pl.botcompetitionarena.domain.library.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
