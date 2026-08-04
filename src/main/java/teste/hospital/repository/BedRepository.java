package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Bed;

public interface BedRepository extends JpaRepository<Bed, Long> {
}
