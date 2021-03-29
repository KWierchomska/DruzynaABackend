package ieit.agh.edu.pl.botcompetitionarena.domain.vote.entity;

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
}
