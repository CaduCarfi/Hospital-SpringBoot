package teste.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.hospital.enums.BedStatus;

@Entity
@Table(name = "bed")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int bedNumber;
    @Enumerated(EnumType.STRING)
    private BedStatus status;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
