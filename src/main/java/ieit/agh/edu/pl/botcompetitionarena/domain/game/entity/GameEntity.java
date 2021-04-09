package ieit.agh.edu.pl.botcompetitionarena.domain.game.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "game")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String version;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] payload;

    public GameEntity(String name, String version, byte[] payload) {
        this.name = name;
        this.version = version;
        this.payload = payload;
    }
}
