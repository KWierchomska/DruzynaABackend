package ieit.agh.edu.pl.botcompetitionarena.domain.team.entity;

import ieit.agh.edu.pl.botcompetitionarena.domain.bot.entity.BotEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.teammember.entity.TeamMemberEntity;
import ieit.agh.edu.pl.botcompetitionarena.domain.vote.entity.VoteEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    private String name;

    @OneToMany(mappedBy="team")
    private Set<TeamMemberEntity> teamMembers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_leader_id", referencedColumnName = "id")
    private TeamMemberEntity teamLeader;

    @OneToMany(mappedBy="team")
    private Set<VoteEntity> votes;

    @OneToMany(mappedBy="team")
    private Set<BotEntity> bots;

    public TeamEntity(String name) {
        this.name = name;
    }
}
