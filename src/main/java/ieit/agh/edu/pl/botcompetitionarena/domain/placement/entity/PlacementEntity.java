package ieit.agh.edu.pl.botcompetitionarena.domain.placement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "placement")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
