package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.enums.BedStatus;
import teste.hospital.model.Bed;
import teste.hospital.model.Room;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findByRoom_Ward_SpecialtyAndStatus(String specialty, BedStatus status);

    long countByRoom(Room room);
}
