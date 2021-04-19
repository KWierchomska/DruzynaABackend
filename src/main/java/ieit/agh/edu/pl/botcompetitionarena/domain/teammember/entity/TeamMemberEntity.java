package ieit.agh.edu.pl.botcompetitionarena.domain.teammember.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.team.entity.TeamEntity;
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
    private String name;
    private String surname;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name="team_id")
    private TeamEntity team;

    public TeamMemberEntity(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
