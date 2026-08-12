package teste.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.hospital.enums.RoomStatus;

import java.util.List;

@Entity
@Table(name = "room")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode;
    private RoomStatus status;
    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Bed> beds;
}
