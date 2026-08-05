package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Room;
import teste.hospital.model.Ward;

public interface RoomRepository extends JpaRepository<Room, Long> {
    long countByWard(Ward ward);
}
