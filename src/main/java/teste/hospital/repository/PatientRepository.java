package teste.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.hospital.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
