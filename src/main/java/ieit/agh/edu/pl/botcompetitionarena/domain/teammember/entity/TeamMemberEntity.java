package ieit.agh.edu.pl.botcompetitionarena.domain.teammember.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "team_member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
