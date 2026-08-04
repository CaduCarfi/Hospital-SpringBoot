package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
