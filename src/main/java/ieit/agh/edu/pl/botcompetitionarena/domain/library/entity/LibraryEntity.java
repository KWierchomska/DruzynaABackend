package ieit.agh.edu.pl.botcompetitionarena.domain.library.entity;

import lombok.*;

import javax.persistence.*;

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
}
