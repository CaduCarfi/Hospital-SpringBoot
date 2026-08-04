package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Ward;

public interface WardRepository extends JpaRepository<Ward, Long> {
}
