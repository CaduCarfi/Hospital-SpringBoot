package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.AdmissionLog;

public interface AdmissionLogRepository extends JpaRepository<AdmissionLog, Long> {
}
