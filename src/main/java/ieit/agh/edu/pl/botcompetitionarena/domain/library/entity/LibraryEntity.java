package ieit.agh.edu.pl.botcompetitionarena.domain.library.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity.QueueEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.vote.entity.VoteEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "library")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String version;
    private StateOfDefinition stateOfDefinition;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="library_queue_assignment",
    joinColumns = {@JoinColumn(name="library_id")},
    inverseJoinColumns = { @JoinColumn(name="queue_id")})
    Set<QueueEntity> queues = new HashSet<>();

    @OneToMany(mappedBy="library")
    private Set<VoteEntity> votes;

    public LibraryEntity(String name, String version, StateOfDefinition stateOfDefinition) {
        this.name = name;
        this.version = version;
        this.stateOfDefinition = stateOfDefinition;
    }
}
