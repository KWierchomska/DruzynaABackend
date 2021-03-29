package ieit.agh.edu.pl.botcompetitionarena.domain.queue.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "queue")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
