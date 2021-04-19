package ieit.agh.edu.pl.botcompetitionarena.domain.vote.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.library.entity.LibraryEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.team.entity.TeamEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean vote;

    @ManyToOne
    @JoinColumn(name="team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name="library_id")
    private LibraryEntity library;

    public VoteEntity(boolean vote) {
        this.vote = vote;
    }
}
