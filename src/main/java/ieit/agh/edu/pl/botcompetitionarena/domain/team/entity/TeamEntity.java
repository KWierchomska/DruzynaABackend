package ieit.agh.edu.pl.botcompetitionarena.domain.team.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
