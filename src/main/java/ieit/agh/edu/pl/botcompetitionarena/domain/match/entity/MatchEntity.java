package ieit.agh.edu.pl.botcompetitionarena.domain.match.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "match")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
