package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
